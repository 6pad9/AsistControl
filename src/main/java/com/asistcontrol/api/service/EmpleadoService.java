package com.asistcontrol.api.service;

import com.asistcontrol.api.dto.EmpleadoDTO;
import com.asistcontrol.api.entity.Empleado;
import com.asistcontrol.api.entity.Sede;
import com.asistcontrol.api.repository.EmpleadoRepository;
import com.asistcontrol.api.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private SedeRepository sedeRepository;
    
    public List<EmpleadoDTO> obtenerTodos() {
        return empleadoRepository.findByActivoTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<EmpleadoDTO> obtenerPorId(Long id) {
        return empleadoRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public Optional<EmpleadoDTO> obtenerPorCodigo(String codigo) {
        return empleadoRepository.findByCodigo(codigo)
                .map(this::convertToDTO);
    }
    
    public List<EmpleadoDTO> obtenerPorSede(Long sedeId) {
        return empleadoRepository.findBySedeIdAndActivoTrue(sedeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<EmpleadoDTO> buscarEmpleados(String termino) {
        return empleadoRepository.buscarEmpleados(termino).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EmpleadoDTO crear(EmpleadoDTO empleadoDTO) {
        validarEmpleadoUnico(empleadoDTO);
        
        Empleado empleado = convertToEntity(empleadoDTO);
        Sede sede = sedeRepository.findById(empleadoDTO.getSedeId())
                .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
        
        empleado.setSede(sede);
        empleado = empleadoRepository.save(empleado);
        
        return convertToDTO(empleado);
    }
    
    public EmpleadoDTO actualizar(Long id, EmpleadoDTO empleadoDTO) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        
        validarEmpleadoUnicoActualizacion(empleadoDTO, id);
        
        // Update fields
        empleado.setCodigo(empleadoDTO.getCodigo());
        empleado.setNombres(empleadoDTO.getNombres());
        empleado.setApellidos(empleadoDTO.getApellidos());
        empleado.setNumeroDocumento(empleadoDTO.getNumeroDocumento());
        empleado.setTipoDocumento(empleadoDTO.getTipoDocumento());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleado.setFechaIngreso(empleadoDTO.getFechaIngreso());
        empleado.setCargo(empleadoDTO.getCargo());
        empleado.setDepartamento(empleadoDTO.getDepartamento());
        empleado.setActivo(empleadoDTO.getActivo());
        
        if (!empleado.getSede().getId().equals(empleadoDTO.getSedeId())) {
            Sede sede = sedeRepository.findById(empleadoDTO.getSedeId())
                    .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
            empleado.setSede(sede);
        }
        
        empleado = empleadoRepository.save(empleado);
        return convertToDTO(empleado);
    }
    
    public void eliminar(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        empleado.setActivo(false);
        empleadoRepository.save(empleado);
    }
    
    private void validarEmpleadoUnico(EmpleadoDTO empleadoDTO) {
        if (empleadoRepository.existsByCodigo(empleadoDTO.getCodigo())) {
            throw new RuntimeException("Ya existe un empleado con el código: " + empleadoDTO.getCodigo());
        }
        if (empleadoRepository.existsByNumeroDocumento(empleadoDTO.getNumeroDocumento())) {
            throw new RuntimeException("Ya existe un empleado con el documento: " + empleadoDTO.getNumeroDocumento());
        }
    }
    
    private void validarEmpleadoUnicoActualizacion(EmpleadoDTO empleadoDTO, Long id) {
        Optional<Empleado> empleadoExistenteCodigo = empleadoRepository.findByCodigo(empleadoDTO.getCodigo());
        if (empleadoExistenteCodigo.isPresent() && !empleadoExistenteCodigo.get().getId().equals(id)) {
            throw new RuntimeException("Ya existe un empleado con el código: " + empleadoDTO.getCodigo());
        }
        
        Optional<Empleado> empleadoExistenteDocumento = empleadoRepository.findByNumeroDocumento(empleadoDTO.getNumeroDocumento());
        if (empleadoExistenteDocumento.isPresent() && !empleadoExistenteDocumento.get().getId().equals(id)) {
            throw new RuntimeException("Ya existe un empleado con el documento: " + empleadoDTO.getNumeroDocumento());
        }
    }
    
    private EmpleadoDTO convertToDTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setCodigo(empleado.getCodigo());
        dto.setNombres(empleado.getNombres());
        dto.setApellidos(empleado.getApellidos());
        dto.setNumeroDocumento(empleado.getNumeroDocumento());
        dto.setTipoDocumento(empleado.getTipoDocumento());
        dto.setEmail(empleado.getEmail());
        dto.setTelefono(empleado.getTelefono());
        dto.setFechaNacimiento(empleado.getFechaNacimiento());
        dto.setFechaIngreso(empleado.getFechaIngreso());
        dto.setCargo(empleado.getCargo());
        dto.setDepartamento(empleado.getDepartamento());
        dto.setSedeId(empleado.getSede().getId());
        dto.setSedeName(empleado.getSede().getNombre());
        dto.setActivo(empleado.getActivo());
        return dto;
    }
    
    private Empleado convertToEntity(EmpleadoDTO dto) {
        Empleado empleado = new Empleado();
        empleado.setCodigo(dto.getCodigo());
        empleado.setNombres(dto.getNombres());
        empleado.setApellidos(dto.getApellidos());
        empleado.setNumeroDocumento(dto.getNumeroDocumento());
        empleado.setTipoDocumento(dto.getTipoDocumento());
        empleado.setEmail(dto.getEmail());
        empleado.setTelefono(dto.getTelefono());
        empleado.setFechaNacimiento(dto.getFechaNacimiento());
        empleado.setFechaIngreso(dto.getFechaIngreso());
        empleado.setCargo(dto.getCargo());
        empleado.setDepartamento(dto.getDepartamento());
        empleado.setActivo(dto.getActivo());
        return empleado;
    }
}