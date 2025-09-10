package com.asistcontrol.api.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GeoLocationService {
    
    private static final double EARTH_RADIUS = 6371000; // Radio de la Tierra en metros
    
    /**
     * Calcula la distancia entre dos puntos geográficos usando la fórmula Haversine
     * 
     * @param lat1 Latitud del punto 1
     * @param lon1 Longitud del punto 1
     * @param lat2 Latitud del punto 2
     * @param lon2 Longitud del punto 2
     * @return Distancia en metros
     */
    public double calcularDistancia(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        double latitud1 = Math.toRadians(lat1.doubleValue());
        double longitud1 = Math.toRadians(lon1.doubleValue());
        double latitud2 = Math.toRadians(lat2.doubleValue());
        double longitud2 = Math.toRadians(lon2.doubleValue());
        
        double diferenciaLatitud = latitud2 - latitud1;
        double diferenciaLongitud = longitud2 - longitud1;
        
        double a = Math.sin(diferenciaLatitud / 2) * Math.sin(diferenciaLatitud / 2) +
                   Math.cos(latitud1) * Math.cos(latitud2) *
                   Math.sin(diferenciaLongitud / 2) * Math.sin(diferenciaLongitud / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return EARTH_RADIUS * c;
    }
    
    /**
     * Verifica si un punto está dentro del radio de geocerca de otro punto
     * 
     * @param latitudPunto Latitud del punto a verificar
     * @param longitudPunto Longitud del punto a verificar
     * @param latitudCentro Latitud del centro de la geocerca
     * @param longitudCentro Longitud del centro de la geocerca
     * @param radioMetros Radio de la geocerca en metros
     * @return true si está dentro de la geocerca
     */
    public boolean estaDentroDeGeocerca(BigDecimal latitudPunto, BigDecimal longitudPunto,
                                       BigDecimal latitudCentro, BigDecimal longitudCentro,
                                       double radioMetros) {
        double distancia = calcularDistancia(latitudPunto, longitudPunto, latitudCentro, longitudCentro);
        return distancia <= radioMetros;
    }
    
    /**
     * Valida que las coordenadas GPS sean válidas
     * 
     * @param latitud Latitud a validar
     * @param longitud Longitud a validar
     * @return true si las coordenadas son válidas
     */
    public boolean coordenadasValidas(BigDecimal latitud, BigDecimal longitud) {
        if (latitud == null || longitud == null) {
            return false;
        }
        
        double lat = latitud.doubleValue();
        double lon = longitud.doubleValue();
        
        return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
    }
}