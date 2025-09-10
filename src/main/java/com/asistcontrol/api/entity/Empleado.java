package com.asistcontrol.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "empleados")
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El código de empleado es requerido")
    @Size(max = 20, message = "El código no puede exceder 20 caracteres")
    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;
    
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    
    @NotBlank(message = "Los apellidos son requeridos")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    
    @NotBlank(message = "El número de documento es requerido")
    @Size(max = 20, message = "El documento no puede exceder 20 caracteres")
    @Column(name = "numero_documento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento = TipoDocumento.DNI;
    
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    @Column(name = "email", length = 100)
    private String email;
    
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso = LocalDate.now();
    
    @Size(max = 100, message = "El cargo no puede exceder 100 caracteres")
    @Column(name = "cargo", length = 100)
    private String cargo;
    
    @Size(max = 100, message = "El departamento no puede exceder 100 caracteres")
    @Column(name = "departamento", length = 100)
    private String departamento;
    
    // Datos biométricos
    @Column(name = "huella_digital", columnDefinition = "TEXT")
    private String huellaDigital; // Base64 encoded fingerprint template
    
    @Column(name = "foto", columnDefinition = "TEXT")
    private String foto; // Base64 encoded photo
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;
    
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Asistencia> asistencias;
    
    public enum TipoDocumento {
        DNI, CARNET_EXTRANJERIA, PASAPORTE
    }
    
    // Constructors
    public Empleado() {}
    
    public Empleado(String codigo, String nombres, String apellidos, String numeroDocumento, Sede sede) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroDocumento = numeroDocumento;
        this.sede = sede;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getNumeroDocumento() {
        return numeroDocumento;
    }
    
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }
    
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    public String getCargo() {
        return cargo;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    public String getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public String getHuellaDigital() {
        return huellaDigital;
    }
    
    public void setHuellaDigital(String huellaDigital) {
        this.huellaDigital = huellaDigital;
    }
    
    public String getFoto() {
        return foto;
    }
    
    public void setFoto(String foto) {
        this.foto = foto;
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
    
    public Sede getSede() {
        return sede;
    }
    
    public void setSede(Sede sede) {
        this.sede = sede;
    }
    
    public Set<Asistencia> getAsistencias() {
        return asistencias;
    }
    
    public void setAsistencias(Set<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
    
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}