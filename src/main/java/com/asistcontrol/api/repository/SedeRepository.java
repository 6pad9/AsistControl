package com.asistcontrol.api.repository;

import com.asistcontrol.api.entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {
    
    List<Sede> findByEmpresaId(Long empresaId);
    
    List<Sede> findByActivoTrue();
    
    List<Sede> findByEmpresaIdAndActivoTrue(Long empresaId);
    
    List<Sede> findByNombreContainingIgnoreCase(String nombre);
}