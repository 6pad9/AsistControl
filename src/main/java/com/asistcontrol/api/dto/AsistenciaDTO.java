package com.asistcontrol.api.dto;

import com.asistcontrol.api.entity.Asistencia.TipoRegistro;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AsistenciaDTO {
    
    private Long id;
    
    @NotNull(message = "El empleado es requerido")
    private Long empleadoId;
    
    private String empleadoNombre;
    private String empleadoCodigo;
    
    @NotNull(message = "El tipo de registro es requerido")
    private TipoRegistro tipoRegistro;
    
    private LocalDateTime fechaHora;
    
    @NotNull(message = "La latitud es requerida")
    private BigDecimal latitud;
    
    @NotNull(message = "La longitud es requerida")
    private BigDecimal longitud;
    
    private Double distanciaSede;
    private Boolean dentroGeocerca;
    private Boolean huellaVerificada;
    private String fotoRegistro;
    private Boolean reconocimientoFacial;
    private String dispositivo;
    private String observaciones;
    private Boolean validado;
    
    // Constructor
    public AsistenciaDTO() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getEmpleadoId() {
        return empleadoId;
    }
    
    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }
    
    public String getEmpleadoNombre() {
        return empleadoNombre;
    }
    
    public void setEmpleadoNombre(String empleadoNombre) {
        this.empleadoNombre = empleadoNombre;
    }
    
    public String getEmpleadoCodigo() {
        return empleadoCodigo;
    }
    
    public void setEmpleadoCodigo(String empleadoCodigo) {
        this.empleadoCodigo = empleadoCodigo;
    }
    
    public TipoRegistro getTipoRegistro() {
        return tipoRegistro;
    }
    
    public void setTipoRegistro(TipoRegistro tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public BigDecimal getLatitud() {
        return latitud;
    }
    
    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }
    
    public BigDecimal getLongitud() {
        return longitud;
    }
    
    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }
    
    public Double getDistanciaSede() {
        return distanciaSede;
    }
    
    public void setDistanciaSede(Double distanciaSede) {
        this.distanciaSede = distanciaSede;
    }
    
    public Boolean getDentroGeocerca() {
        return dentroGeocerca;
    }
    
    public void setDentroGeocerca(Boolean dentroGeocerca) {
        this.dentroGeocerca = dentroGeocerca;
    }
    
    public Boolean getHuellaVerificada() {
        return huellaVerificada;
    }
    
    public void setHuellaVerificada(Boolean huellaVerificada) {
        this.huellaVerificada = huellaVerificada;
    }
    
    public String getFotoRegistro() {
        return fotoRegistro;
    }
    
    public void setFotoRegistro(String fotoRegistro) {
        this.fotoRegistro = fotoRegistro;
    }
    
    public Boolean getReconocimientoFacial() {
        return reconocimientoFacial;
    }
    
    public void setReconocimientoFacial(Boolean reconocimientoFacial) {
        this.reconocimientoFacial = reconocimientoFacial;
    }
    
    public String getDispositivo() {
        return dispositivo;
    }
    
    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public Boolean getValidado() {
        return validado;
    }
    
    public void setValidado(Boolean validado) {
        this.validado = validado;
    }
}