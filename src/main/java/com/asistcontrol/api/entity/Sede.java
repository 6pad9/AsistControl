package com.asistcontrol.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "sedes")
public class Sede {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre de la sede es requerido")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    @Column(name = "direccion", length = 200)
    private String direccion;
    
    @NotNull(message = "La latitud es requerida")
    @Column(name = "latitud", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitud;
    
    @NotNull(message = "La longitud es requerida")
    @Column(name = "longitud", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitud;
    
    @Column(name = "radio_geocerca")
    private Integer radioGeocerca = 100; // metros
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
    
    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Empleado> empleados;
    
    // Constructors
    public Sede() {}
    
    public Sede(String nombre, BigDecimal latitud, BigDecimal longitud, Empresa empresa) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.empresa = empresa;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
    
    public Integer getRadioGeocerca() {
        return radioGeocerca;
    }
    
    public void setRadioGeocerca(Integer radioGeocerca) {
        this.radioGeocerca = radioGeocerca;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Empresa getEmpresa() {
        return empresa;
    }
    
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public Set<Empleado> getEmpleados() {
        return empleados;
    }
    
    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }
}