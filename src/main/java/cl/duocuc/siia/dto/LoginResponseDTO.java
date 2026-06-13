package cl.duocuc.siia.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Respuesta con token JWT")
public class LoginResponseDTO {
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;
}