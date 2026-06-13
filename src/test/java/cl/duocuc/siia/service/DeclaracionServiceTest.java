package cl.duocuc.siia.service;

import cl.duocuc.siia.dto.DeclaracionRequestDTO;
import cl.duocuc.siia.dto.DeclaracionResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DeclaracionServiceTest {

    @InjectMocks
    private DeclaracionService declaracionService;

    @Test
    void procesarDeclaracion_conAlimentos_shouldIncludeSagResponse() {
        DeclaracionRequestDTO request = new DeclaracionRequestDTO();
        request.setPortaAlimentos(true);
        request.setPortaMascota(false);

        DeclaracionResponseDTO response = declaracionService.procesarDeclaracion(request);

        assertThat(response.getMensaje()).contains("Alimentos");
        assertThat(response.getEstadoPdi()).isEqualTo("VERIFICACIÓN MIGRATORIA APROBADA");
    }

    @Test
    void procesarDeclaracion_conMascota_shouldIncludeSagResponse() {
        DeclaracionRequestDTO request = new DeclaracionRequestDTO();
        request.setPortaAlimentos(false);
        request.setPortaMascota(true);

        DeclaracionResponseDTO response = declaracionService.procesarDeclaracion(request);

        assertThat(response.getMensaje()).contains("Mascota");
    }

    @Test
    void procesarDeclaracion_sinAlimentosNiMascota_shouldReturnWithoutSagItems() {
        DeclaracionRequestDTO request = new DeclaracionRequestDTO();
        request.setPortaAlimentos(false);
        request.setPortaMascota(false);

        DeclaracionResponseDTO response = declaracionService.procesarDeclaracion(request);

        assertThat(response.getMensaje()).contains("Sin declaración");
        assertThat(response.getMensaje()).doesNotContain("Alimentos").doesNotContain("Mascota");
    }
}