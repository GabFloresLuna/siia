package cl.duocuc.siia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duocuc.siia.model.Tramite;

public interface TramiteRepository extends JpaRepository<Tramite, Long> {
    Optional<Tramite> findByFolio(String folio);
}