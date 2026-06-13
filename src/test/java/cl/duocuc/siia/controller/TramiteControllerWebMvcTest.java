package cl.duocuc.siia.controller;
 
import cl.duocuc.siia.dto.TramiteDTO;
import cl.duocuc.siia.exception.ResourceNotFoundException;
import cl.duocuc.siia.service.TramiteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; 
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TramiteController.class)
class TramiteControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TramiteService tramiteService;
 

    @Test
    void listar_ShouldReturnPagedTramites() throws Exception {
        // Crear un DTO de ejemplo
        TramiteDTO dto = new TramiteDTO();
        dto.setId(1L);
        dto.setFolio("FOL-123456");
        dto.setPatente("CL·TX45");
        dto.setEstado("Pendiente de revisión");
        dto.setFecha(LocalDateTime.now());

        // Simular respuesta paginada del servicio
        Page<TramiteDTO> page = new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1);
        when(tramiteService.listarTodos(any(Pageable.class))).thenReturn(page);

        // Ejecutar petición GET con parámetros de paginación
        mockMvc.perform(get("/api/v1/tramites")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "fecha,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Trámites obtenidos correctamente"))
                .andExpect(jsonPath("$.data.content[0].folio").value("FOL-123456"))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.totalPages").value(1));
    }

    @Test
    void obtenerPorFolio_WhenExists_ShouldReturnTramite() throws Exception {
        TramiteDTO dto = new TramiteDTO();
        dto.setId(1L);
        dto.setFolio("FOL-123456");
        dto.setPatente("CL·TX45");
        dto.setEstado("Pendiente de revisión");

        when(tramiteService.obtenerPorFolio("FOL-123456")).thenReturn(dto);

        mockMvc.perform(get("/api/v1/tramites/FOL-123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Trámite encontrado"))
                .andExpect(jsonPath("$.data.folio").value("FOL-123456"))
                .andExpect(jsonPath("$.data.patente").value("CL·TX45"));
    }

    @Test
    void obtenerPorFolio_WhenNotFound_ShouldReturn404() throws Exception {
        when(tramiteService.obtenerPorFolio("FOL-999999"))
                .thenThrow(new ResourceNotFoundException("Trámite con folio FOL-999999 no encontrado"));

        mockMvc.perform(get("/api/v1/tramites/FOL-999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Trámite con folio FOL-999999 no encontrado"));
    }

    @Test
    void crearTramiteVehiculo_ShouldReturnCreated() throws Exception {
        TramiteDTO response = new TramiteDTO();
        response.setId(1L);
        response.setFolio("FOL-1734567890123");
        response.setPatente("CL-123");
        response.setEstado("Pendiente de revisión");
        response.setFecha(LocalDateTime.now());

        when(tramiteService.crearTramiteVehiculo("CL-123")).thenReturn(response);

        mockMvc.perform(post("/api/v1/tramites/vehiculo")
                        .param("patente", "CL-123"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Trámite creado exitosamente"))
                .andExpect(jsonPath("$.data.folio").value("FOL-1734567890123"))
                .andExpect(jsonPath("$.data.patente").value("CL-123"));
    }
}