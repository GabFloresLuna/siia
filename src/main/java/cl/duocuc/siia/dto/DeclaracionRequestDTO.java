package cl.duocuc.siia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Solicitud de declaración SAG/PDI")
public class DeclaracionRequestDTO {

    @NotNull
    @Schema(description = "Indica si porta alimentos", example = "true")
    private Boolean portaAlimentos;

    @NotNull
    @Schema(description = "Indica si porta mascota", example = "false")
    private Boolean portaMascota;

    @Schema(description = "ID del pasajero (opcional, para asociar)", example = "5")
    private Long pasajeroId;
}