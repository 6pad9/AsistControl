package com.asistcontrol.api.service;

import com.asistcontrol.api.dto.AsistenciaDTO;
import com.asistcontrol.api.dto.RegistroAsistenciaDTO;
import com.asistcontrol.api.entity.Asistencia;
import com.asistcontrol.api.entity.Empleado;
import com.asistcontrol.api.repository.AsistenciaRepository;
import com.asistcontrol.api.repository.EmpleadoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AsistenciaService {
    
    @Autowired
    private AsistenciaRepository asistenciaRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private GeoLocationService geoLocationService;
    
    @Autowired
    private BiometricService biometricService;
    
    @Autowired
    private HttpServletRequest request;
    
    @Value("${gps.geofence.radius:100}")
    private double geofenceRadius;
    
    public List<AsistenciaDTO> obtenerTodas() {
        return asistenciaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<AsistenciaDTO> obtenerPorEmpleado(Long empleadoId) {
        return asistenciaRepository.findByEmpleadoIdOrderByFechaHoraDesc(empleadoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<AsistenciaDTO> obtenerPorEmpleadoYFecha(Long empleadoId, LocalDate fecha) {
        return asistenciaRepository.findByEmpleadoIdAndFecha(empleadoId, fecha).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<AsistenciaDTO> obtenerPorSedeYFecha(Long sedeId, LocalDate fecha) {
        return asistenciaRepository.findBySedeIdAndFecha(sedeId, fecha).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public AsistenciaDTO registrarAsistencia(RegistroAsistenciaDTO registroDTO) {
        // Buscar empleado por código
        Empleado empleado = empleadoRepository.findByCodigo(registroDTO.getEmpleadoCodigo())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con código: " + registroDTO.getEmpleadoCodigo()));
        
        if (!empleado.getActivo()) {
            throw new RuntimeException("El empleado está inactivo");
        }
        
        // Validar tipo de registro
        validarTipoRegistro(empleado.getId(), registroDTO.getTipoRegistro());
        
        // Crear asistencia
        Asistencia asistencia = new Asistencia();
        asistencia.setEmpleado(empleado);
        asistencia.setTipoRegistro(registroDTO.getTipoRegistro());
        asistencia.setLatitud(registroDTO.getLatitud());
        asistencia.setLongitud(registroDTO.getLongitud());
        asistencia.setFotoRegistro(registroDTO.getFotoRegistro());
        asistencia.setDispositivo(registroDTO.getDispositivo());
        asistencia.setObservaciones(registroDTO.getObservaciones());
        asistencia.setIpAddress(getClientIpAddress());
        
        // Validar ubicación GPS
        double distancia = geoLocationService.calcularDistancia(
                registroDTO.getLatitud(), registroDTO.getLongitud(),
                empleado.getSede().getLatitud(), empleado.getSede().getLongitud()
        );
        
        asistencia.setDistanciaSede(distancia);
        asistencia.setDentroGeocerca(distancia <= empleado.getSede().getRadioGeocerca());
        
        // Validar biométrica si se proporciona
        if (registroDTO.getHuellaDigital() != null && empleado.getHuellaDigital() != null) {
            boolean huellaValida = biometricService.verificarHuella(
                    registroDTO.getHuellaDigital(), empleado.getHuellaDigital()
            );
            asistencia.setHuellaVerificada(huellaValida);
        }
        
        // Validar reconocimiento facial si se proporciona foto
        if (registroDTO.getFotoRegistro() != null && empleado.getFoto() != null) {
            boolean fotoValida = biometricService.verificarRostro(
                    registroDTO.getFotoRegistro(), empleado.getFoto()
            );
            asistencia.setReconocimientoFacial(fotoValida);
        }
        
        // Auto-validar si cumple criterios
        asistencia.setValidado(determinarValidacionAutomatica(asistencia));
        if (asistencia.getValidado()) {
            asistencia.setFechaValidacion(LocalDateTime.now());
        }
        
        asistencia = asistenciaRepository.save(asistencia);
        return convertToDTO(asistencia);
    }
    
    public AsistenciaDTO validarAsistencia(Long asistenciaId, boolean validar, String observaciones) {
        Asistencia asistencia = asistenciaRepository.findById(asistenciaId)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));
        
        asistencia.setValidado(validar);
        asistencia.setFechaValidacion(LocalDateTime.now());
        if (observaciones != null) {
            asistencia.setObservaciones(observaciones);
        }
        
        asistencia = asistenciaRepository.save(asistencia);
        return convertToDTO(asistencia);
    }
    
    public List<AsistenciaDTO> obtenerAsistenciasPendientes() {
        return asistenciaRepository.findByValidadoFalse().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private void validarTipoRegistro(Long empleadoId, Asistencia.TipoRegistro tipoRegistro) {
        LocalDate hoy = LocalDate.now();
        
        switch (tipoRegistro) {
            case ENTRADA:
                // Verificar si ya tiene entrada hoy
                long entradas = asistenciaRepository.countEntradasByEmpleadoAndFecha(empleadoId, hoy);
                if (entradas > 0) {
                    throw new RuntimeException("El empleado ya registró entrada hoy");
                }
                break;
                
            case SALIDA:
                // Verificar si tiene entrada sin salida
                Optional<Asistencia> ultimaEntrada = asistenciaRepository.findLastByEmpleadoIdAndFechaAndTipo(
                        empleadoId, hoy, Asistencia.TipoRegistro.ENTRADA);
                
                if (ultimaEntrada.isEmpty()) {
                    throw new RuntimeException("No se encontró registro de entrada para hoy");
                }
                
                Optional<Asistencia> ultimaSalida = asistenciaRepository.findLastByEmpleadoIdAndFechaAndTipo(
                        empleadoId, hoy, Asistencia.TipoRegistro.SALIDA);
                
                if (ultimaSalida.isPresent() && 
                    ultimaSalida.get().getFechaHora().isAfter(ultimaEntrada.get().getFechaHora())) {
                    throw new RuntimeException("El empleado ya registró salida");
                }
                break;
                
            case PAUSA:
                // Similar logic for break validation
                break;
                
            case RETORNO_PAUSA:
                // Similar logic for return from break validation
                break;
        }
    }
    
    private boolean determinarValidacionAutomatica(Asistencia asistencia) {
        // Auto-validar si:
        // 1. Está dentro de la geocerca
        // 2. Tiene verificación biométrica exitosa (huella o rostro)
        return asistencia.getDentroGeocerca() && 
               (Boolean.TRUE.equals(asistencia.getHuellaVerificada()) || 
                Boolean.TRUE.equals(asistencia.getReconocimientoFacial()));
    }
    
    private String getClientIpAddress() {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xForwardedForHeader.split(",")[0];
        }
    }
    
    private AsistenciaDTO convertToDTO(Asistencia asistencia) {
        AsistenciaDTO dto = new AsistenciaDTO();
        dto.setId(asistencia.getId());
        dto.setEmpleadoId(asistencia.getEmpleado().getId());
        dto.setEmpleadoNombre(asistencia.getEmpleado().getNombreCompleto());
        dto.setEmpleadoCodigo(asistencia.getEmpleado().getCodigo());
        dto.setTipoRegistro(asistencia.getTipoRegistro());
        dto.setFechaHora(asistencia.getFechaHora());
        dto.setLatitud(asistencia.getLatitud());
        dto.setLongitud(asistencia.getLongitud());
        dto.setDistanciaSede(asistencia.getDistanciaSede());
        dto.setDentroGeocerca(asistencia.getDentroGeocerca());
        dto.setHuellaVerificada(asistencia.getHuellaVerificada());
        dto.setFotoRegistro(asistencia.getFotoRegistro());
        dto.setReconocimientoFacial(asistencia.getReconocimientoFacial());
        dto.setDispositivo(asistencia.getDispositivo());
        dto.setObservaciones(asistencia.getObservaciones());
        dto.setValidado(asistencia.getValidado());
        return dto;
    }
}