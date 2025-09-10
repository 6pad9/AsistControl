package com.asistcontrol.api.controller;

import com.asistcontrol.api.dto.AsistenciaDTO;
import com.asistcontrol.api.dto.RegistroAsistenciaDTO;
import com.asistcontrol.api.service.AsistenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@Tag(name = "Asistencias", description = "API para gestión de asistencias y registro de entrada/salida")
public class AsistenciaController {
    
    @Autowired
    private AsistenciaService asistenciaService;
    
    @GetMapping
    @Operation(summary = "Obtener todas las asistencias", description = "Retorna lista de todas las asistencias")
    public ResponseEntity<List<AsistenciaDTO>> obtenerTodas() {
        List<AsistenciaDTO> asistencias = asistenciaService.obtenerTodas();
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/empleado/{empleadoId}")
    @Operation(summary = "Obtener asistencias por empleado", description = "Retorna las asistencias de un empleado específico")
    public ResponseEntity<List<AsistenciaDTO>> obtenerPorEmpleado(
            @Parameter(description = "ID del empleado") @PathVariable Long empleadoId) {
        List<AsistenciaDTO> asistencias = asistenciaService.obtenerPorEmpleado(empleadoId);
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/empleado/{empleadoId}/fecha/{fecha}")
    @Operation(summary = "Obtener asistencias por empleado y fecha", description = "Retorna las asistencias de un empleado en una fecha específica")
    public ResponseEntity<List<AsistenciaDTO>> obtenerPorEmpleadoYFecha(
            @Parameter(description = "ID del empleado") @PathVariable Long empleadoId,
            @Parameter(description = "Fecha (formato: yyyy-MM-dd)") @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<AsistenciaDTO> asistencias = asistenciaService.obtenerPorEmpleadoYFecha(empleadoId, fecha);
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/sede/{sedeId}/fecha/{fecha}")
    @Operation(summary = "Obtener asistencias por sede y fecha", description = "Retorna las asistencias de una sede en una fecha específica")
    public ResponseEntity<List<AsistenciaDTO>> obtenerPorSedeYFecha(
            @Parameter(description = "ID de la sede") @PathVariable Long sedeId,
            @Parameter(description = "Fecha (formato: yyyy-MM-dd)") @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<AsistenciaDTO> asistencias = asistenciaService.obtenerPorSedeYFecha(sedeId, fecha);
        return ResponseEntity.ok(asistencias);
    }
    
    @PostMapping("/registrar")
    @Operation(summary = "Registrar asistencia", description = "Registra entrada, salida, pausa o retorno de pausa con validación GPS y biométrica")
    public ResponseEntity<?> registrarAsistencia(@Valid @RequestBody RegistroAsistenciaDTO registroDTO) {
        try {
            AsistenciaDTO asistencia = asistenciaService.registrarAsistencia(registroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(asistencia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar asistencia: " + e.getMessage());
        }
    }
    
    @GetMapping("/pendientes")
    @Operation(summary = "Obtener asistencias pendientes de validación", description = "Retorna las asistencias que requieren validación manual")
    public ResponseEntity<List<AsistenciaDTO>> obtenerPendientes() {
        List<AsistenciaDTO> asistencias = asistenciaService.obtenerAsistenciasPendientes();
        return ResponseEntity.ok(asistencias);
    }
    
    @PutMapping("/{asistenciaId}/validar")
    @Operation(summary = "Validar asistencia", description = "Valida o rechaza una asistencia manualmente")
    public ResponseEntity<?> validarAsistencia(
            @Parameter(description = "ID de la asistencia") @PathVariable Long asistenciaId,
            @Parameter(description = "True para validar, false para rechazar") @RequestParam boolean validar,
            @Parameter(description = "Observaciones adicionales") @RequestParam(required = false) String observaciones) {
        try {
            AsistenciaDTO asistencia = asistenciaService.validarAsistencia(asistenciaId, validar, observaciones);
            return ResponseEntity.ok(asistencia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al validar asistencia: " + e.getMessage());
        }
    }
    
    @GetMapping("/hoy")
    @Operation(summary = "Obtener asistencias de hoy", description = "Retorna todas las asistencias registradas el día de hoy")
    public ResponseEntity<List<AsistenciaDTO>> obtenerAsistenciasHoy() {
        LocalDate hoy = LocalDate.now();
        List<AsistenciaDTO> asistencias = asistenciaService.obtenerTodas().stream()
                .filter(a -> a.getFechaHora().toLocalDate().equals(hoy))
                .toList();
        return ResponseEntity.ok(asistencias);
    }
}