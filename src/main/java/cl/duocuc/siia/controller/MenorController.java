package cl.duocuc.siia.controller;

import cl.duocuc.siia.dto.ApiResponse;
import cl.duocuc.siia.dto.MenorValidacionRequestDTO;
import cl.duocuc.siia.dto.MenorValidacionResponseDTO;
import cl.duocuc.siia.service.MenorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menores")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Menores", description = "Validación de autorizaciones notariales para menores")
public class MenorController {

    private final MenorService menorService;
    private static final Logger logger = LoggerFactory.getLogger(MenorController.class);

    @PostMapping("/validar")
    @Operation(summary = "Validar autorización notarial de un menor")
    public ResponseEntity<ApiResponse<MenorValidacionResponseDTO>> validar(@Valid @RequestBody MenorValidacionRequestDTO request) {
        MenorValidacionResponseDTO resultado = menorService.validarAutorizacion(request);
        logger.info("Validación de menor - RUN: {}, Resultado: {}", request.getRun(), resultado.isValida());
        return ResponseEntity.ok(new ApiResponse<>(200, resultado.getMensaje(), resultado));
    }
}