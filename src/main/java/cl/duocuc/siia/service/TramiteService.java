package cl.duocuc.siia.service;

import cl.duocuc.siia.dto.TramiteDTO;
import cl.duocuc.siia.exception.ResourceNotFoundException;
import cl.duocuc.siia.model.Tramite;
import cl.duocuc.siia.repository.TramiteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TramiteService {

    private final TramiteRepository tramiteRepository;
    private final ModelMapper modelMapper;

    public Page<TramiteDTO> listarTodos(Pageable pageable) {
        Page<Tramite> page = tramiteRepository.findAll(pageable);
        return page.map(this::convertirADTO);
    }

    public TramiteDTO obtenerPorFolio(String folio) {
        Tramite tramite = tramiteRepository.findByFolio(folio)
                .orElseThrow(() -> new ResourceNotFoundException("Trámite con folio " + folio + " no encontrado"));
        return convertirADTO(tramite);
    }

    public TramiteDTO crearTramiteVehiculo(String patente) {
        String folio = generarFolio();
        Tramite tramite = new Tramite();
        tramite.setFolio(folio);
        tramite.setPatente(patente);
        tramite.setEstado("Pendiente de revisión");
        tramite.setFecha(LocalDateTime.now());
        Tramite guardado = tramiteRepository.save(tramite);
        return convertirADTO(guardado);
    }

    private String generarFolio() {
        return "FOL-" + System.currentTimeMillis();
    }

    private TramiteDTO convertirADTO(Tramite entidad) {
        return modelMapper.map(entidad, TramiteDTO.class);
    }
}