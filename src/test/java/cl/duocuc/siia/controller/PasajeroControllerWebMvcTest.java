package cl.duocuc.siia.controller;
 
import cl.duocuc.siia.dto.PasajeroDTO;
import cl.duocuc.siia.service.PasajeroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PasajeroController.class)
class PasajeroControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PasajeroService pasajeroService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_shouldReturnOkWithPagedApiResponse() throws Exception {
        PasajeroDTO dto = new PasajeroDTO();
        dto.setId(1L);
        dto.setNombre("Juan Perez");
        dto.setDocumento("12.345.678-9");
        dto.setNacionalidad("Chilena");
        dto.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        Page<PasajeroDTO> page = new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1);
        when(pasajeroService.listarTodos(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/pasajeros")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Pasajeros obtenidos correctamente"))
                .andExpect(jsonPath("$.data.content[0].nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.totalPages").value(1));
    }

    @Test
    void registrar_shouldReturnCreatedWithApiResponse() throws Exception {
        PasajeroDTO requestDTO = new PasajeroDTO();
        requestDTO.setNombre("Maria Gomez");
        requestDTO.setNacionalidad("Argentina");
        requestDTO.setDocumento("98.765.432-1");
        requestDTO.setFechaNacimiento(LocalDate.of(1985, 5, 20));

        PasajeroDTO responseDTO = new PasajeroDTO();
        responseDTO.setId(2L);
        responseDTO.setNombre("Maria Gomez");
        responseDTO.setNacionalidad("Argentina");
        responseDTO.setDocumento("98.765.432-1");
        responseDTO.setFechaNacimiento(LocalDate.of(1985, 5, 20));

        when(pasajeroService.registrar(any(PasajeroDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/pasajeros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Pasajero registrado exitosamente"))
                .andExpect(jsonPath("$.data.nombre").value("Maria Gomez"));
    }
}