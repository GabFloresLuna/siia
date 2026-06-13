package cl.duocuc.siia.service;

import cl.duocuc.siia.dto.DeclaracionRequestDTO;
import cl.duocuc.siia.dto.DeclaracionResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class DeclaracionService {

    private static final Logger logger = LoggerFactory.getLogger(DeclaracionService.class);
    private final Random random = new Random();

    public DeclaracionResponseDTO procesarDeclaracion(DeclaracionRequestDTO request) {
        logger.info("Procesando declaración: alimentos={}, mascota={}, pasajeroId={}",
                request.getPortaAlimentos(), request.getPortaMascota(), request.getPasajeroId());

        // Simular comunicación con SAG
        String estadoSag = procesarSag(request);
        // Simular comunicación con PDI
        String estadoPdi = procesarPdi();

        String mensaje = construirMensaje(request, estadoSag, estadoPdi);

        return new DeclaracionResponseDTO(mensaje, estadoSag, estadoPdi, LocalDateTime.now());
    }

    private String procesarSag(DeclaracionRequestDTO request) {
        // Simular latencia y validación
        if (request.getPortaAlimentos()) {
            // Simular 95% de éxito
            return random.nextDouble() < 0.95 ? "APROBADO" : "RECHAZADO - Productos no permitidos";
        }
        if (request.getPortaMascota()) {
            return random.nextDouble() < 0.90 ? "CERTIFICADO SANITARIO VÁLIDO" : "CERTIFICADO INVÁLIDO";
        }
        return "SIN DECLARACIÓN";
    }

    private String procesarPdi() {
        // Simular respuesta PDI (siempre aprobada para el demo)
        return "VERIFICACIÓN MIGRATORIA APROBADA";
    }

    private String construirMensaje(DeclaracionRequestDTO request, String sag, String pdi) {
        StringBuilder sb = new StringBuilder();
        if (request.getPortaAlimentos()) {
            sb.append("📦 Alimentos: ").append(sag).append(". ");
        }
        if (request.getPortaMascota()) {
            sb.append("🐕 Mascota: ").append(sag).append(". ");
        }
        if (!request.getPortaAlimentos() && !request.getPortaMascota()) {
            sb.append("✅ Sin declaración de alimentos ni mascotas. ");
        }
        sb.append("PDI: ").append(pdi);
        return sb.toString();
    }
}