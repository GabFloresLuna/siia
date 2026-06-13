-- 1. Tabla de pasajeros
CREATE TABLE IF NOT EXISTS pasajeros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(50) NOT NULL,
    documento VARCHAR(20) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    fecha_registro DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Tabla de trámites vehiculares
CREATE TABLE IF NOT EXISTS tramites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    folio VARCHAR(20) NOT NULL UNIQUE,
    patente VARCHAR(20) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- (Opcional) Índices para búsquedas frecuentes
CREATE INDEX idx_pasajeros_documento ON pasajeros(documento);
CREATE INDEX idx_tramites_folio ON tramites(folio);
CREATE INDEX idx_tramites_patente ON tramites(patente);