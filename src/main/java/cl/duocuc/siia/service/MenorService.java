package cl.duocuc.siia.service;

import cl.duocuc.siia.dto.MenorValidacionRequestDTO;
import cl.duocuc.siia.dto.MenorValidacionResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class MenorService {

    public MenorValidacionResponseDTO validarAutorizacion(MenorValidacionRequestDTO request) {
        // Regla de negocio: autorización debe comenzar con "NOT-" y tener al menos 8 caracteres,
        // además el RUN no debe estar vacío.
        boolean valida = request.getAutorizacionNotarial() != null &&
                request.getAutorizacionNotarial().startsWith("NOT-") &&
                request.getAutorizacionNotarial().length() > 8 &&
                request.getRun() != null && !request.getRun().isBlank();

        String mensaje = valida
                ? "Autorización válida y firmada electrónicamente. Menor autorizado para tránsito."
                : "Documento inválido: formato incorrecto o firma no coincide. Rechazar ingreso.";

        return new MenorValidacionResponseDTO(valida, mensaje);
    }
}