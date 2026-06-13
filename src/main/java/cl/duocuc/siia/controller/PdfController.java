package cl.duocuc.siia.controller;

import cl.duocuc.siia.dto.PdfVehiculoRequestDTO;
import cl.duocuc.siia.dto.TramiteDTO;
import cl.duocuc.siia.service.PdfGeneradorService;
import cl.duocuc.siia.service.TramiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pdf")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "PDF", description = "Generación de documentos PDF (formularios aduaneros)")
public class PdfController {

    private final PdfGeneradorService pdfGeneradorService;
    private final TramiteService tramiteService;
    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);

    @PostMapping("/vehiculo")
    @Operation(summary = "Generar formulario PDF de admisión temporal de vehículo")
    public ResponseEntity<byte[]> generarPdfVehiculo(@Valid @RequestBody PdfVehiculoRequestDTO request) {
        // Si no se proporcionó folio, creamos un nuevo trámite y obtenemos el folio generado
        if (request.getFolio() == null || request.getFolio().isBlank()) {
            TramiteDTO nuevoTramite = tramiteService.crearTramiteVehiculo(request.getPatente());
            request.setFolio(nuevoTramite.getFolio());
            logger.info("Nuevo folio generado para PDF: {}", nuevoTramite.getFolio());
        }

        byte[] pdfBytes = pdfGeneradorService.generarFormularioVehiculo(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "formulario_vehiculo_" + request.getFolio() + ".pdf");

        logger.info("PDF generado para patente: {}, folio: {}", request.getPatente(), request.getFolio());
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}