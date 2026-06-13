package cl.duocuc.siia.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tramites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tramite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false, length = 20)
    private String folio;                // Ej: FOL-000001

    @NotBlank
    @Column(nullable = false, length = 20)
    private String patente;

    @NotBlank
    @Column(nullable = false)
    private String estado;               // "Pendiente de revisión", "Aprobado", etc.

    @NotNull
    @Column(nullable = false)
    private LocalDateTime fecha;

    // Constructor útil (sin id)
    public Tramite(String folio, String patente, String estado, LocalDateTime fecha) {
        this.folio = folio;
        this.patente = patente;
        this.estado = estado;
        this.fecha = fecha;
    }
}