package cl.duocuc.siia.service;

import cl.duocuc.siia.dto.TramiteDTO;
import cl.duocuc.siia.exception.ResourceNotFoundException;
import cl.duocuc.siia.model.Tramite;
import cl.duocuc.siia.repository.TramiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TramiteServiceTest {

    @Mock
    private TramiteRepository tramiteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TramiteService tramiteService;

    private Tramite tramite;
    private TramiteDTO tramiteDTO;

    @BeforeEach
    void setUp() {
        tramite = new Tramite();
        tramite.setId(1L);
        tramite.setFolio("FOL-123456");
        tramite.setPatente("CL·TX45");
        tramite.setEstado("Pendiente de revisión");
        tramite.setFecha(LocalDateTime.now());

        tramiteDTO = new TramiteDTO();
        tramiteDTO.setId(1L);
        tramiteDTO.setFolio("FOL-123456");
        tramiteDTO.setPatente("CL·TX45");
        tramiteDTO.setEstado("Pendiente de revisión");
        tramiteDTO.setFecha(LocalDateTime.now());
    }

    @Test
    void listarTodos_withPagination_shouldReturnPageOfDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Tramite> page = new PageImpl<>(List.of(tramite), pageable, 1);
        when(tramiteRepository.findAll(pageable)).thenReturn(page);
        when(modelMapper.map(tramite, TramiteDTO.class)).thenReturn(tramiteDTO);

        Page<TramiteDTO> result = tramiteService.listarTodos(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getFolio()).isEqualTo("FOL-123456");
    }

    @Test
    void obtenerPorFolio_existingFolio_shouldReturnDTO() {
        when(tramiteRepository.findByFolio("FOL-123456")).thenReturn(Optional.of(tramite));
        when(modelMapper.map(tramite, TramiteDTO.class)).thenReturn(tramiteDTO);

        TramiteDTO result = tramiteService.obtenerPorFolio("FOL-123456");

        assertThat(result).isNotNull();
        assertThat(result.getPatente()).isEqualTo("CL·TX45");
        verify(tramiteRepository, times(1)).findByFolio("FOL-123456");
    }

    @Test
    void obtenerPorFolio_nonExistingFolio_shouldThrowException() {
        when(tramiteRepository.findByFolio("FOL-XXXXX")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            tramiteService.obtenerPorFolio("FOL-XXXXX");
        });
    }

    @Test
    void crearTramiteVehiculo_shouldGenerateFolioAndSave() {
        when(tramiteRepository.save(any(Tramite.class))).thenReturn(tramite);
        when(modelMapper.map(tramite, TramiteDTO.class)).thenReturn(tramiteDTO);

        TramiteDTO result = tramiteService.crearTramiteVehiculo("CL·TX45");

        assertThat(result).isNotNull();
        assertThat(result.getFolio()).startsWith("FOL-");
        verify(tramiteRepository, times(1)).save(any(Tramite.class));
    }
}