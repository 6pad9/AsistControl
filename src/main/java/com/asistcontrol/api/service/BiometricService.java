package com.asistcontrol.api.service;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class BiometricService {
    
    /**
     * Verifica si dos huellas dactilares coinciden
     * En una implementación real, se usaría una librería especializada en biometría
     * 
     * @param huellaCapturada Huella capturada en el registro (Base64)
     * @param huellaRegistrada Huella registrada del empleado (Base64)
     * @return true si las huellas coinciden
     */
    public boolean verificarHuella(String huellaCapturada, String huellaRegistrada) {
        if (huellaCapturada == null || huellaRegistrada == null) {
            return false;
        }
        
        try {
            // Validar que ambas sean Base64 válidas
            byte[] capturada = Base64.getDecoder().decode(huellaCapturada);
            byte[] registrada = Base64.getDecoder().decode(huellaRegistrada);
            
            // Simulación simple de comparación
            // En producción se usaría una librería como SourceAFIS o similar
            double similitud = calcularSimilitudSimulada(capturada, registrada);
            
            // Umbral de similitud del 85%
            return similitud >= 0.85;
            
        } catch (IllegalArgumentException e) {
            // Error en decodificación Base64
            return false;
        }
    }
    
    /**
     * Verifica si dos rostros coinciden mediante reconocimiento facial
     * En una implementación real, se usaría una librería de reconocimiento facial
     * 
     * @param fotoCapturada Foto capturada en el registro (Base64)
     * @param fotoRegistrada Foto registrada del empleado (Base64)
     * @return true si los rostros coinciden
     */
    public boolean verificarRostro(String fotoCapturada, String fotoRegistrada) {
        if (fotoCapturada == null || fotoRegistrada == null) {
            return false;
        }
        
        try {
            // Validar que ambas sean Base64 válidas
            byte[] capturada = Base64.getDecoder().decode(fotoCapturada);
            byte[] registrada = Base64.getDecoder().decode(fotoRegistrada);
            
            // Simulación simple de comparación facial
            // En producción se usaría OpenCV, Face++, AWS Rekognition, etc.
            double similitud = calcularSimilitudFacialSimulada(capturada, registrada);
            
            // Umbral de similitud del 80%
            return similitud >= 0.80;
            
        } catch (IllegalArgumentException e) {
            // Error en decodificación Base64
            return false;
        }
    }
    
    /**
     * Valida que una imagen Base64 sea válida
     * 
     * @param imagenBase64 Imagen en formato Base64
     * @return true si la imagen es válida
     */
    public boolean validarImagenBase64(String imagenBase64) {
        if (imagenBase64 == null || imagenBase64.trim().isEmpty()) {
            return false;
        }
        
        try {
            // Remover prefijo de data URL si existe
            String base64Data = imagenBase64;
            if (imagenBase64.startsWith("data:image/")) {
                base64Data = imagenBase64.substring(imagenBase64.indexOf(",") + 1);
            }
            
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            
            // Validar tamaño mínimo y máximo
            return decodedBytes.length > 100 && decodedBytes.length < 5_000_000; // 5MB max
            
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Simulación de cálculo de similitud entre huellas
     * En producción se reemplazaría por algoritmo real
     */
    private double calcularSimilitudSimulada(byte[] huella1, byte[] huella2) {
        // Simulación muy básica basada en tamaño y algunos bytes
        if (huella1.length == 0 || huella2.length == 0) {
            return 0.0;
        }
        
        // Comparar tamaños
        double similitudTamaño = 1.0 - Math.abs(huella1.length - huella2.length) / (double) Math.max(huella1.length, huella2.length);
        
        // Comparar primeros bytes
        int bytesAComparar = Math.min(Math.min(huella1.length, huella2.length), 100);
        int bytesIguales = 0;
        
        for (int i = 0; i < bytesAComparar; i++) {
            if (huella1[i] == huella2[i]) {
                bytesIguales++;
            }
        }
        
        double similitudBytes = (double) bytesIguales / bytesAComparar;
        
        // Promedio ponderado
        return (similitudTamaño * 0.3) + (similitudBytes * 0.7);
    }
    
    /**
     * Simulación de cálculo de similitud facial
     * En producción se reemplazaría por algoritmo real
     */
    private double calcularSimilitudFacialSimulada(byte[] foto1, byte[] foto2) {
        // Simulación muy básica
        if (foto1.length == 0 || foto2.length == 0) {
            return 0.0;
        }
        
        // Para la simulación, asumimos alta similitud si las fotos tienen tamaño similar
        double similitudTamaño = 1.0 - Math.abs(foto1.length - foto2.length) / (double) Math.max(foto1.length, foto2.length);
        
        // En un sistema real, aquí se extraerían características faciales y se compararían
        return Math.min(similitudTamaño + 0.1, 0.95); // Agregar algo de "ruido" positivo
    }
}