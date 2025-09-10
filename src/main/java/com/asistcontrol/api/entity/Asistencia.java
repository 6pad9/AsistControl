package com.asistcontrol.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El empleado es requerido")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;
    
    @NotNull(message = "El tipo de registro es requerido")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_registro", nullable = false)
    private TipoRegistro tipoRegistro;
    
    @NotNull(message = "La fecha y hora son requeridas")
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();
    
    // Datos GPS
    @Column(name = "latitud", precision = 10, scale = 7)
    private BigDecimal latitud;
    
    @Column(name = "longitud", precision = 10, scale = 7)
    private BigDecimal longitud;
    
    @Column(name = "distancia_sede")
    private Double distanciaSede; // distancia en metros desde la sede
    
    @Column(name = "dentro_geocerca")
    private Boolean dentroGeocerca = false;
    
    // Datos biométricos
    @Column(name = "huella_verificada")
    private Boolean huellaVerificada = false;
    
    @Column(name = "foto_registro", columnDefinition = "TEXT")
    private String fotoRegistro; // Base64 encoded photo taken during registration
    
    @Column(name = "reconocimiento_facial")
    private Boolean reconocimientoFacial = false;
    
    // Metadata del registro
    @Column(name = "dispositivo")
    private String dispositivo;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "validado")
    private Boolean validado = false;
    
    @Column(name = "fecha_validacion")
    private LocalDateTime fechaValidacion;
    
    public enum TipoRegistro {
        ENTRADA, SALIDA, PAUSA, RETORNO_PAUSA
    }
    
    // Constructors
    public Asistencia() {}
    
    public Asistencia(Empleado empleado, TipoRegistro tipoRegistro, BigDecimal latitud, BigDecimal longitud) {
        this.empleado = empleado;
        this.tipoRegistro = tipoRegistro;
        this.latitud = latitud;
        this.longitud = longitud;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
    
    public LocalDateTime getFechaValidacion() {
        return fechaValidacion;
    }
    
    public void setFechaValidacion(LocalDateTime fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }
}