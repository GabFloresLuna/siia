package cl.duocuc.siia.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa a un pasajero que cruza la frontera")
public class PasajeroDTO {

    @Schema(description = "Identificador único en la base de datos", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Schema(description = "Nombre completo del pasajero", example = "Juan Carlos Pérez González", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "La nacionalidad es obligatoria")
    @Size(max = 50, message = "La nacionalidad no puede exceder 50 caracteres")
    @Schema(description = "Nacionalidad declarada", example = "Chilena", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nacionalidad;

    @NotBlank(message = "El número de documento (RUN/Pasaporte) es obligatorio")
    @Size(max = 20, message = "El documento no puede exceder 20 caracteres")
    @Schema(description = "RUN chileno o número de pasaporte", example = "12.345.678-9", requiredMode = Schema.RequiredMode.REQUIRED)
    private String documento;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser pasada")
    @Schema(description = "Fecha de nacimiento en formato ISO (YYYY-MM-DD)", example = "1985-06-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaNacimiento;
}