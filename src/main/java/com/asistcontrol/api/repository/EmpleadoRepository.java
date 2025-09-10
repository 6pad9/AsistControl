package com.asistcontrol.api.repository;

import com.asistcontrol.api.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
    Optional<Empleado> findByCodigo(String codigo);
    
    Optional<Empleado> findByNumeroDocumento(String numeroDocumento);
    
    List<Empleado> findBySedeId(Long sedeId);
    
    List<Empleado> findByActivoTrue();
    
    List<Empleado> findBySedeIdAndActivoTrue(Long sedeId);
    
    @Query("SELECT e FROM Empleado e WHERE " +
           "(LOWER(e.nombres) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(e.apellidos) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(e.codigo) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(e.numeroDocumento) LIKE LOWER(CONCAT('%', :termino, '%'))) " +
           "AND e.activo = true")
    List<Empleado> buscarEmpleados(@Param("termino") String termino);
    
    boolean existsByCodigo(String codigo);
    
    boolean existsByNumeroDocumento(String numeroDocumento);
}