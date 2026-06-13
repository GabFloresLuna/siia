package cl.duocuc.siia.exception;

import cl.duocuc.siia.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Manejo de errores de validación (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.joining(", "));
        logger.warn("Error de validación: {}", errors);
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "Error de validación: " + errors, null));
    }

    // Violación de restricciones (ej. @NotNull, @Size, etc. en consultas)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        logger.warn("Restricción violada: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "Error de validación: " + ex.getMessage(), null));
    }

    // Datos duplicados o violación de integridad (ej. documento único)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        logger.error("Violación de integridad de datos: {}", ex.getMessage());
        String message = "Error de datos: posible duplicado de documento o referencia inválida.";
        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            if (ex.getCause().getMessage().contains("Duplicate entry")) {
                message = "El documento ya existe en el sistema.";
            }
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(409, message, null));
    }

    // Recurso no encontrado (puedes lanzar esta excepción en servicios)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(404, ex.getMessage(), null));
    }

    // Error de tipo de parámetro (ej. pasar texto donde se espera Long)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("El parámetro '%s' debe ser de tipo %s", ex.getName(), ex.getRequiredType().getSimpleName());
        logger.warn(message);
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, message, null));
    }

    // JSON mal formado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        logger.warn("Cuerpo de solicitud inválido: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "Cuerpo de la solicitud inválido o mal formado", null));
    }

    // Captura genérica para cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        logger.error("Error interno no controlado: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(500, "Error interno del servidor. Contacte al administrador.", null));
    }
}