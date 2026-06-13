package cl.duocuc.siia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta del proceso de declaración SAG/PDI")
public class DeclaracionResponseDTO {
    private String mensaje;
    private String estadoSag;
    private String estadoPdi;
    private LocalDateTime fechaProcesamiento;
}