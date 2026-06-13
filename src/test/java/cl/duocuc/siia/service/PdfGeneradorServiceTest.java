package cl.duocuc.siia.service;

import cl.duocuc.siia.dto.PdfVehiculoRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class PdfGeneradorServiceTest {

    @InjectMocks
    private PdfGeneradorService pdfGeneradorService;

    @Test
    void generarFormularioVehiculo_shouldReturnNonEmptyByteArray() {
        PdfVehiculoRequestDTO request = new PdfVehiculoRequestDTO();
        request.setPatente("CL·TX45");
        request.setTitular("Gabriel Flores");
        request.setTipoVehiculo("Automóvil");
        request.setFolio("FOL-123");

        byte[] pdf = pdfGeneradorService.generarFormularioVehiculo(request);

        assertThat(pdf).isNotEmpty();
        // Opcional: verificar que comienza con "%PDF" (mágico)
        String pdfStart = new String(pdf, 0, 4);
        assertThat(pdfStart).isEqualTo("%PDF");
    }

    @Test
    void generarFormularioVehiculo_shouldNotThrowException() {
        PdfVehiculoRequestDTO request = new PdfVehiculoRequestDTO();
        request.setPatente("CL-123");
        request.setTitular("Test");
        request.setTipoVehiculo("Camioneta");

        assertDoesNotThrow(() -> pdfGeneradorService.generarFormularioVehiculo(request));
    }
}