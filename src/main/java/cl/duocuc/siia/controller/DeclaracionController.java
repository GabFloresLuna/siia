package cl.duocuc.siia.controller;

import cl.duocuc.siia.dto.ApiResponse;
import cl.duocuc.siia.dto.DeclaracionRequestDTO;
import cl.duocuc.siia.dto.DeclaracionResponseDTO;
import cl.duocuc.siia.service.DeclaracionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/declaraciones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Declaraciones SAG/PDI", description = "Gestión de declaraciones juradas de alimentos y mascotas")
public class DeclaracionController {

    private final DeclaracionService declaracionService;
    private static final Logger logger = LoggerFactory.getLogger(DeclaracionController.class);

    @PostMapping
    @Operation(summary = "Procesar declaración SAG/PDI")
    public ResponseEntity<ApiResponse<DeclaracionResponseDTO>> procesar(@Valid @RequestBody DeclaracionRequestDTO request) {
        DeclaracionResponseDTO resultado = declaracionService.procesarDeclaracion(request);
        logger.info("Declaración procesada - Alimentos: {}, Mascota: {}", request.getPortaAlimentos(), request.getPortaMascota());
        return ResponseEntity.ok(new ApiResponse<>(200, "Declaración procesada exitosamente", resultado));
    }
}