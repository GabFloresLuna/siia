package cl.duocuc.siia.service;

import cl.duocuc.siia.dto.PasajeroDTO;
import cl.duocuc.siia.model.Pasajero;
import cl.duocuc.siia.repository.PasajeroRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasajeroService {

    private final PasajeroRepository pasajeroRepository;
    private final ModelMapper modelMapper;

    public Page<PasajeroDTO> listarTodos(Pageable pageable) {
        Page<Pasajero> page = pasajeroRepository.findAll(pageable);
        return page.map(this::convertirADTO);
    }

    public PasajeroDTO registrar(PasajeroDTO dto) {
        Pasajero pasajero = modelMapper.map(dto, Pasajero.class);
        pasajero.setFechaRegistro(LocalDateTime.now());
        Pasajero guardado = pasajeroRepository.save(pasajero);
        return convertirADTO(guardado);
    }

    private PasajeroDTO convertirADTO(Pasajero entidad) {
        return modelMapper.map(entidad, PasajeroDTO.class);
    }
}