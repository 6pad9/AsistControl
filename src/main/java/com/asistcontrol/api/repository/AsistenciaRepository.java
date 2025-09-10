package com.asistcontrol.api.repository;

import com.asistcontrol.api.entity.Asistencia;
import com.asistcontrol.api.entity.Asistencia.TipoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    
    List<Asistencia> findByEmpleadoId(Long empleadoId);
    
    List<Asistencia> findByEmpleadoIdOrderByFechaHoraDesc(Long empleadoId);
    
    @Query("SELECT a FROM Asistencia a WHERE a.empleado.id = :empleadoId " +
           "AND CAST(a.fechaHora AS DATE) = :fecha ORDER BY a.fechaHora DESC")
    List<Asistencia> findByEmpleadoIdAndFecha(@Param("empleadoId") Long empleadoId, 
                                             @Param("fecha") LocalDate fecha);
    
    @Query("SELECT a FROM Asistencia a WHERE a.empleado.id = :empleadoId " +
           "AND a.fechaHora BETWEEN :fechaInicio AND :fechaFin ORDER BY a.fechaHora DESC")
    List<Asistencia> findByEmpleadoIdAndFechaHoraBetween(@Param("empleadoId") Long empleadoId,
                                                        @Param("fechaInicio") LocalDateTime fechaInicio,
                                                        @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT a FROM Asistencia a WHERE a.empleado.id = :empleadoId " +
           "AND CAST(a.fechaHora AS DATE) = :fecha AND a.tipoRegistro = :tipoRegistro " +
           "ORDER BY a.fechaHora DESC")
    Optional<Asistencia> findLastByEmpleadoIdAndFechaAndTipo(@Param("empleadoId") Long empleadoId,
                                                           @Param("fecha") LocalDate fecha,
                                                           @Param("tipoRegistro") TipoRegistro tipoRegistro);
    
    @Query("SELECT a FROM Asistencia a WHERE a.empleado.sede.id = :sedeId " +
           "AND CAST(a.fechaHora AS DATE) = :fecha ORDER BY a.fechaHora DESC")
    List<Asistencia> findBySedeIdAndFecha(@Param("sedeId") Long sedeId, 
                                         @Param("fecha") LocalDate fecha);
    
    List<Asistencia> findByValidadoFalse();
    
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.empleado.id = :empleadoId " +
           "AND CAST(a.fechaHora AS DATE) = :fecha AND a.tipoRegistro = 'ENTRADA'")
    long countEntradasByEmpleadoAndFecha(@Param("empleadoId") Long empleadoId, 
                                        @Param("fecha") LocalDate fecha);
}