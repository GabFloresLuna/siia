package cl.duocuc.siia.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para los trámites de admisión temporal de vehículos")
public class TramiteDTO {

    @Schema(description = "Identificador interno", example = "25", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Folio único generado por el sistema", example = "FOL-1734567890123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String folio;

    @Schema(description = "Patente del vehículo (formato chileno o argentino)", example = "CL·TX45", requiredMode = Schema.RequiredMode.REQUIRED)
    private String patente;

    @Schema(description = "Estado actual del trámite", example = "Pendiente de revisión", allowableValues = {"Pendiente de revisión", "Aprobado", "Rechazado"})
    private String estado;

    @Schema(description = "Fecha y hora de creación del trámite", example = "2026-06-13T15:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fecha;
}