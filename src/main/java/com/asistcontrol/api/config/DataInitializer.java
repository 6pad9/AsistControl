package com.asistcontrol.api.config;

import com.asistcontrol.api.entity.Empresa;
import com.asistcontrol.api.entity.Empleado;
import com.asistcontrol.api.entity.Sede;
import com.asistcontrol.api.repository.EmpresaRepository;
import com.asistcontrol.api.repository.EmpleadoRepository;
import com.asistcontrol.api.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private SedeRepository sedeRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (empresaRepository.count() == 0) {
            initializeTestData();
        }
    }
    
    private void initializeTestData() {
        // Crear empresa de prueba
        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa Demo S.A.C.");
        empresa.setRuc("20123456789");
        empresa.setDireccion("Av. Principal 123, Lima");
        empresa.setTelefono("01-2345678");
        empresa.setEmail("contacto@empresademo.com");
        empresa = empresaRepository.save(empresa);
        
        // Crear sede principal
        Sede sedePrincipal = new Sede();
        sedePrincipal.setNombre("Sede Principal - Lima");
        sedePrincipal.setDireccion("Av. Principal 123, Lima");
        sedePrincipal.setLatitud(new BigDecimal("-12.0464"));
        sedePrincipal.setLongitud(new BigDecimal("-77.0428"));
        sedePrincipal.setRadioGeocerca(100);
        sedePrincipal.setEmpresa(empresa);
        sedePrincipal = sedeRepository.save(sedePrincipal);
        
        // Crear sede sucursal
        Sede sedeSucursal = new Sede();
        sedeSucursal.setNombre("Sucursal Miraflores");
        sedeSucursal.setDireccion("Av. Larco 456, Miraflores");
        sedeSucursal.setLatitud(new BigDecimal("-12.1189"));
        sedeSucursal.setLongitud(new BigDecimal("-77.0315"));
        sedeSucursal.setRadioGeocerca(150);
        sedeSucursal.setEmpresa(empresa);
        sedeSucursal = sedeRepository.save(sedeSucursal);
        
        // Crear empleados de prueba
        Empleado empleado1 = new Empleado();
        empleado1.setCodigo("EMP001");
        empleado1.setNombres("Juan Carlos");
        empleado1.setApellidos("Pérez García");
        empleado1.setNumeroDocumento("12345678");
        empleado1.setTipoDocumento(Empleado.TipoDocumento.DNI);
        empleado1.setEmail("juan.perez@empresademo.com");
        empleado1.setTelefono("987654321");
        empleado1.setFechaNacimiento(LocalDate.of(1985, 5, 15));
        empleado1.setFechaIngreso(LocalDate.of(2020, 1, 15));
        empleado1.setCargo("Analista de Sistemas");
        empleado1.setDepartamento("Tecnología");
        empleado1.setSede(sedePrincipal);
        empleado1.setHuellaDigital("U2FsdGVkX1+E4dYEczY8EnCwQPhMTqjCzQGFJJGNpbI="); // Simulación Base64
        empleado1.setFoto("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="); // Pixel transparente
        empleadoRepository.save(empleado1);
        
        Empleado empleado2 = new Empleado();
        empleado2.setCodigo("EMP002");
        empleado2.setNombres("María Elena");
        empleado2.setApellidos("González López");
        empleado2.setNumeroDocumento("87654321");
        empleado2.setTipoDocumento(Empleado.TipoDocumento.DNI);
        empleado2.setEmail("maria.gonzalez@empresademo.com");
        empleado2.setTelefono("987123456");
        empleado2.setFechaNacimiento(LocalDate.of(1990, 8, 20));
        empleado2.setFechaIngreso(LocalDate.of(2021, 3, 1));
        empleado2.setCargo("Contadora");
        empleado2.setDepartamento("Finanzas");
        empleado2.setSede(sedePrincipal);
        empleado2.setHuellaDigital("U2FsdGVkX19QdGZlYXNlIGVuY29kZSB0aGlzIHRleHQ="); // Simulación Base64
        empleado2.setFoto("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="); // Pixel transparente
        empleadoRepository.save(empleado2);
        
        Empleado empleado3 = new Empleado();
        empleado3.setCodigo("EMP003");
        empleado3.setNombres("Carlos Alberto");
        empleado3.setApellidos("Ramírez Torres");
        empleado3.setNumeroDocumento("11223344");
        empleado3.setTipoDocumento(Empleado.TipoDocumento.DNI);
        empleado3.setEmail("carlos.ramirez@empresademo.com");
        empleado3.setTelefono("987111222");
        empleado3.setFechaNacimiento(LocalDate.of(1988, 12, 10));
        empleado3.setFechaIngreso(LocalDate.of(2019, 6, 15));
        empleado3.setCargo("Supervisor de Ventas");
        empleado3.setDepartamento("Ventas");
        empleado3.setSede(sedeSucursal);
        empleado3.setHuellaDigital("U2FsdGVkX18hcm9kdWN0aW9uIGRhdGEgZm9yIHRlc3Q="); // Simulación Base64
        empleado3.setFoto("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="); // Pixel transparente
        empleadoRepository.save(empleado3);
        
        System.out.println("Datos de prueba inicializados correctamente:");
        System.out.println("- 1 Empresa: " + empresa.getNombre());
        System.out.println("- 2 Sedes: " + sedePrincipal.getNombre() + ", " + sedeSucursal.getNombre());
        System.out.println("- 3 Empleados: EMP001, EMP002, EMP003");
        System.out.println("- Usuario admin: admin/admin123");
        System.out.println("- Usuario normal: user/user123");
    }
}