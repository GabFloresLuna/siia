package cl.duocuc.siia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Solicitud para generar formulario PDF de vehículo")
public class PdfVehiculoRequestDTO {

    @NotBlank
    @Schema(description = "Patente del vehículo", example = "CL·TX45")
    private String patente;

    @NotBlank
    @Schema(description = "Nombre del titular", example = "Gabriel Flores")
    private String titular;

    @NotBlank
    @Schema(description = "Tipo de vehículo", example = "Automóvil Particular")
    private String tipoVehiculo;

    @Schema(description = "Folio generado (opcional, si no se envía se genera uno nuevo)")
    private String folio;
}