package cl.duocuc.siia.controller;

import cl.duocuc.siia.dto.ApiResponse;
import cl.duocuc.siia.dto.TramiteDTO;
import cl.duocuc.siia.service.TramiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/tramites")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Trámites Vehiculares", description = "Gestión de folios de admisión temporal de vehículos")
public class TramiteController {

    private final TramiteService tramiteService;
    private static final Logger logger = LoggerFactory.getLogger(TramiteController.class);

    @GetMapping
    @Operation(summary = "Lista todos los trámites vehiculares (paginado)")
    public ResponseEntity<ApiResponse<Page<TramiteDTO>>> listar(
            @PageableDefault(size = 10, sort = "fecha") Pageable pageable) {
        Page<TramiteDTO> page = tramiteService.listarTodos(pageable);
        logger.info("Listados {} trámites de un total de {}", page.getNumberOfElements(), page.getTotalElements());
        return ResponseEntity.ok(new ApiResponse<>(200, "Trámites obtenidos correctamente", page));
    }

    @GetMapping("/{folio}")
    @Operation(summary = "Obtiene un trámite por su folio")
    public ResponseEntity<ApiResponse<TramiteDTO>> obtenerPorFolio(@PathVariable String folio) {
        TramiteDTO tramite = tramiteService.obtenerPorFolio(folio);
        logger.info("Trámite encontrado con folio: {}", folio);
        return ResponseEntity.ok(new ApiResponse<>(200, "Trámite encontrado", tramite));
    }

    @PostMapping("/vehiculo")
    @Operation(summary = "Crea un nuevo trámite vehicular a partir de la patente")
    public ResponseEntity<ApiResponse<TramiteDTO>> crear(@RequestParam String patente) {
        TramiteDTO nuevo = tramiteService.crearTramiteVehiculo(patente);
        logger.info("Nuevo trámite creado - Folio: {}, Patente: {}", nuevo.getFolio(), nuevo.getPatente());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Trámite creado exitosamente", nuevo));
    }
}