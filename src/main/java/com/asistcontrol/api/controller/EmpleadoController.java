package com.asistcontrol.api.controller;

import com.asistcontrol.api.dto.EmpleadoDTO;
import com.asistcontrol.api.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
@Tag(name = "Empleados", description = "API para gestión de empleados")
public class EmpleadoController {
    
    @Autowired
    private EmpleadoService empleadoService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los empleados activos", description = "Retorna lista de todos los empleados activos")
    public ResponseEntity<List<EmpleadoDTO>> obtenerTodos() {
        List<EmpleadoDTO> empleados = empleadoService.obtenerTodos();
        return ResponseEntity.ok(empleados);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por ID", description = "Retorna un empleado específico por su ID")
    public ResponseEntity<EmpleadoDTO> obtenerPorId(
            @Parameter(description = "ID del empleado") @PathVariable Long id) {
        Optional<EmpleadoDTO> empleado = empleadoService.obtenerPorId(id);
        return empleado.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Obtener empleado por código", description = "Retorna un empleado específico por su código")
    public ResponseEntity<EmpleadoDTO> obtenerPorCodigo(
            @Parameter(description = "Código del empleado") @PathVariable String codigo) {
        Optional<EmpleadoDTO> empleado = empleadoService.obtenerPorCodigo(codigo);
        return empleado.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/sede/{sedeId}")
    @Operation(summary = "Obtener empleados por sede", description = "Retorna lista de empleados activos de una sede específica")
    public ResponseEntity<List<EmpleadoDTO>> obtenerPorSede(
            @Parameter(description = "ID de la sede") @PathVariable Long sedeId) {
        List<EmpleadoDTO> empleados = empleadoService.obtenerPorSede(sedeId);
        return ResponseEntity.ok(empleados);
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar empleados", description = "Busca empleados por nombre, apellido, código o documento")
    public ResponseEntity<List<EmpleadoDTO>> buscarEmpleados(
            @Parameter(description = "Término de búsqueda") @RequestParam String termino) {
        List<EmpleadoDTO> empleados = empleadoService.buscarEmpleados(termino);
        return ResponseEntity.ok(empleados);
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo empleado", description = "Crea un nuevo empleado en el sistema")
    public ResponseEntity<?> crear(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            EmpleadoDTO nuevoEmpleado = empleadoService.crear(empleadoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear empleado: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar empleado", description = "Actualiza los datos de un empleado existente")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID del empleado") @PathVariable Long id,
            @Valid @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            EmpleadoDTO empleadoActualizado = empleadoService.actualizar(id, empleadoDTO);
            return ResponseEntity.ok(empleadoActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar empleado: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar empleado", description = "Marca un empleado como inactivo (eliminación lógica)")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del empleado") @PathVariable Long id) {
        try {
            empleadoService.eliminar(id);
            return ResponseEntity.ok().body("Empleado eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar empleado: " + e.getMessage());
        }
    }
}