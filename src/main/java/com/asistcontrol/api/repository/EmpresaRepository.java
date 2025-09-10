package com.asistcontrol.api.repository;

import com.asistcontrol.api.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
    Optional<Empresa> findByRuc(String ruc);
    
    List<Empresa> findByActivoTrue();
    
    List<Empresa> findByNombreContainingIgnoreCase(String nombre);
    
    boolean existsByRuc(String ruc);
}