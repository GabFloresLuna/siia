package cl.duocuc.siia.controller;

import cl.duocuc.siia.dto.TramiteDTO;
import cl.duocuc.siia.exception.ResourceNotFoundException;
import cl.duocuc.siia.service.TramiteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(TramiteController.class)
class TramiteControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TramiteService tramiteService;

    @Test
    @WithMockUser(roles = "INSPECTOR")
    void listar_ShouldReturnPagedTramites() throws Exception {
        TramiteDTO dto = new TramiteDTO();
        dto.setId(1L);
        dto.setFolio("FOL-123456");
        dto.setPatente("CL·TX45");
        dto.setEstado("Pendiente de revisión");
        dto.setFecha(LocalDateTime.now());

        Page<TramiteDTO> page = new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1);
        when(tramiteService.listarTodos(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/tramites")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].folio").value("FOL-123456"));
    }

    @Test
    @WithMockUser(roles = "INSPECTOR")
    void obtenerPorFolio_WhenExists_ShouldReturnTramite() throws Exception {
        TramiteDTO dto = new TramiteDTO();
        dto.setId(1L);
        dto.setFolio("FOL-123456");
        dto.setPatente("CL·TX45");
        when(tramiteService.obtenerPorFolio("FOL-123456")).thenReturn(dto);

        mockMvc.perform(get("/api/v1/tramites/FOL-123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.folio").value("FOL-123456"));
    }

    @Test
    @WithMockUser(roles = "INSPECTOR")
    void obtenerPorFolio_WhenNotFound_ShouldReturn404() throws Exception {
        when(tramiteService.obtenerPorFolio("FOL-999999"))
                .thenThrow(new ResourceNotFoundException("Trámite con folio FOL-999999 no encontrado"));

        mockMvc.perform(get("/api/v1/tramites/FOL-999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Trámite con folio FOL-999999 no encontrado"));
    }

    @Test
    @WithMockUser(roles = "INSPECTOR")
    void crearTramiteVehiculo_ShouldReturnCreated() throws Exception {
        TramiteDTO response = new TramiteDTO();
        response.setId(1L);
        response.setFolio("FOL-1734567890123");
        response.setPatente("CL-123");
        response.setEstado("Pendiente de revisión");
        when(tramiteService.crearTramiteVehiculo("CL-123")).thenReturn(response);

        mockMvc.perform(post("/api/v1/tramites/vehiculo")
                        .param("patente", "CL-123")
                        .with(csrf()))   // ← Importación estática
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.folio").exists());
    }
}