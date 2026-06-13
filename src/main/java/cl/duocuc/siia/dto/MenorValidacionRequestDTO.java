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
@Schema(description = "Solicitud para validar autorización de menor")
public class MenorValidacionRequestDTO {

    @NotBlank
    @Schema(description = "Nombre completo del menor", example = "Sofia Mendez")
    private String nombre;

    @NotBlank
    @Schema(description = "RUN o pasaporte del menor", example = "25.123.456-7")
    private String run;

    @NotBlank
    @Schema(description = "Número de autorización notarial", example = "NOT-2026-7823")
    private String autorizacionNotarial;
}