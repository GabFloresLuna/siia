package cl.duocuc.siia.controller;

import cl.duocuc.siia.dto.PasajeroDTO;
import cl.duocuc.siia.service.PasajeroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(PasajeroController.class)
class PasajeroControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PasajeroService pasajeroService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    @WithMockUser(roles = "INSPECTOR")
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
                .andExpect(jsonPath("$.data.content[0].nombre").value("Juan Perez"));
    }

    @Test
    @WithMockUser(roles = "INSPECTOR")
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
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .with(csrf()))   // <-- clave para evitar 403
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.nombre").value("Maria Gomez"));
    }
}