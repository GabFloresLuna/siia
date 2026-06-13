package cl.duocuc.siia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resultado de la validación de menor")
public class MenorValidacionResponseDTO {
    private boolean valida;
    private String mensaje;
}