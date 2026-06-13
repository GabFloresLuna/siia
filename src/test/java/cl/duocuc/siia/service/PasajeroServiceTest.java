package cl.duocuc.siia.service;

import cl.duocuc.siia.dto.PasajeroDTO;
import cl.duocuc.siia.model.Pasajero;
import cl.duocuc.siia.repository.PasajeroRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasajeroServiceTest {

    @Mock
    private PasajeroRepository pasajeroRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PasajeroService pasajeroService;

    private Pasajero pasajero;
    private PasajeroDTO pasajeroDTO;

    @BeforeEach
    void setUp() {
        pasajero = new Pasajero();
        pasajero.setId(1L);
        pasajero.setNombre("Juan Perez");
        pasajero.setNacionalidad("Chilena");
        pasajero.setDocumento("12.345.678-9");
        pasajero.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        pasajero.setFechaRegistro(LocalDateTime.now());

        pasajeroDTO = new PasajeroDTO();
        pasajeroDTO.setId(1L);
        pasajeroDTO.setNombre("Juan Perez");
        pasajeroDTO.setNacionalidad("Chilena");
        pasajeroDTO.setDocumento("12.345.678-9");
        pasajeroDTO.setFechaNacimiento(LocalDate.of(1990, 1, 1));
    }

    @Test
    void listarTodos_withPagination_shouldReturnPageOfDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Pasajero> page = new PageImpl<>(List.of(pasajero), pageable, 1);
        when(pasajeroRepository.findAll(pageable)).thenReturn(page);
        when(modelMapper.map(pasajero, PasajeroDTO.class)).thenReturn(pasajeroDTO);

        Page<PasajeroDTO> result = pasajeroService.listarTodos(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getNombre()).isEqualTo("Juan Perez");
        verify(pasajeroRepository, times(1)).findAll(pageable);
    }

    @Test
    void registrar_shouldSaveAndReturnDTO() {
        when(modelMapper.map(pasajeroDTO, Pasajero.class)).thenReturn(pasajero);
        when(pasajeroRepository.save(any(Pasajero.class))).thenReturn(pasajero);
        when(modelMapper.map(pasajero, PasajeroDTO.class)).thenReturn(pasajeroDTO);

        PasajeroDTO result = pasajeroService.registrar(pasajeroDTO);

        assertThat(result).isNotNull();
        assertThat(result.getDocumento()).isEqualTo("12.345.678-9");
        verify(pasajeroRepository, times(1)).save(any(Pasajero.class));
    }
}