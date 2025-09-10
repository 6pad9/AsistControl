package com.asistcontrol.api.dto;

import com.asistcontrol.api.entity.Asistencia.TipoRegistro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RegistroAsistenciaDTO {
    
    @NotBlank(message = "El código de empleado es requerido")
    private String empleadoCodigo;
    
    @NotNull(message = "El tipo de registro es requerido")
    private TipoRegistro tipoRegistro;
    
    @NotNull(message = "La latitud es requerida")
    private BigDecimal latitud;
    
    @NotNull(message = "La longitud es requerida")
    private BigDecimal longitud;
    
    private String huellaDigital; // Base64 encoded fingerprint for verification
    private String fotoRegistro; // Base64 encoded photo
    private String dispositivo;
    private String observaciones;
    
    // Constructor
    public RegistroAsistenciaDTO() {}
    
    // Getters and Setters
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
    
    public String getHuellaDigital() {
        return huellaDigital;
    }
    
    public void setHuellaDigital(String huellaDigital) {
        this.huellaDigital = huellaDigital;
    }
    
    public String getFotoRegistro() {
        return fotoRegistro;
    }
    
    public void setFotoRegistro(String fotoRegistro) {
        this.fotoRegistro = fotoRegistro;
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
}