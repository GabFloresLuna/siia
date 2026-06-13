package cl.duocuc.siia.controller;

import cl.duocuc.siia.dto.ApiResponse;
import cl.duocuc.siia.dto.PasajeroDTO;
import cl.duocuc.siia.service.PasajeroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pasajeros")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Pasajeros", description = "Operaciones CRUD para gestionar pasajeros que cruzan la frontera")
public class PasajeroController {

    private final PasajeroService pasajeroService;
    private static final Logger logger = LoggerFactory.getLogger(PasajeroController.class);

    @GetMapping
    @Operation(summary = "Lista todos los pasajeros registrados (paginado)")
    public ResponseEntity<ApiResponse<Page<PasajeroDTO>>> listar(
            @PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        Page<PasajeroDTO> page = pasajeroService.listarTodos(pageable);
        logger.info("Listados {} pasajeros de un total de {}", page.getNumberOfElements(), page.getTotalElements());
        return ResponseEntity.ok(new ApiResponse<>(200, "Pasajeros obtenidos correctamente", page));
    }

    @PostMapping
    @Operation(summary = "Registra un nuevo pasajero")
    public ResponseEntity<ApiResponse<PasajeroDTO>> registrar(@Valid @RequestBody PasajeroDTO dto) {
        PasajeroDTO nuevo = pasajeroService.registrar(dto);
        logger.info("Pasajero registrado: {} (documento {})", nuevo.getNombre(), nuevo.getDocumento());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Pasajero registrado exitosamente", nuevo));
    }
}