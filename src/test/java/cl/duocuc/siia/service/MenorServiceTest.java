package cl.duocuc.siia.service;

import cl.duocuc.siia.dto.MenorValidacionRequestDTO;
import cl.duocuc.siia.dto.MenorValidacionResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MenorServiceTest {

    @InjectMocks
    private MenorService menorService;

    @Test
    void validarAutorizacion_validData_shouldReturnTrue() {
        MenorValidacionRequestDTO request = new MenorValidacionRequestDTO();
        request.setNombre("Sofia");
        request.setRun("25.123.456-7");
        request.setAutorizacionNotarial("NOT-2026-7823");

        MenorValidacionResponseDTO response = menorService.validarAutorizacion(request);

        assertThat(response.isValida()).isTrue();
        assertThat(response.getMensaje()).contains("válida");
    }

    @Test
    void validarAutorizacion_invalidFormat_shouldReturnFalse() {
        MenorValidacionRequestDTO request = new MenorValidacionRequestDTO();
        request.setRun("25.123.456-7");
        request.setAutorizacionNotarial("INV-123"); // no empieza con NOT-

        MenorValidacionResponseDTO response = menorService.validarAutorizacion(request);

        assertThat(response.isValida()).isFalse();
        assertThat(response.getMensaje()).contains("inválido");
    }

    @Test
    void validarAutorizacion_missingRun_shouldReturnFalse() {
        MenorValidacionRequestDTO request = new MenorValidacionRequestDTO();
        request.setAutorizacionNotarial("NOT-2026-7823");
        request.setRun("");

        MenorValidacionResponseDTO response = menorService.validarAutorizacion(request);

        assertThat(response.isValida()).isFalse();
    }
}