package cl.duocuc.siia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duocuc.siia.model.Pasajero;

public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
    // Puedes agregar métodos de búsqueda personalizados si los necesitas
}