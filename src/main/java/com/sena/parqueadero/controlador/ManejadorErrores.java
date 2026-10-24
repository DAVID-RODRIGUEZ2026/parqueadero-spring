package com.sena.parqueadero.controlador;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Manejador global de errores de la API.
 * Atrapa las excepciones que ocurren en los controladores y devuelve
 * una respuesta JSON clara, en espanol, con el codigo HTTP adecuado.
 *
 * @author Manuel David Rodriguez Garcia - SENA ficha 3336117
 */
@RestControllerAdvice
public class ManejadorErrores {

    /**
     * Errores de validacion de los campos (@NotBlank, @Size, @Pattern,
     * @Past, etc.). Responde 400 con el mensaje de cada campo invalido.
     *
     * @param ex excepcion lanzada por @Valid
     * @return respuesta 400 con la lista de errores por campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(
            MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        return construirRespuesta(HttpStatus.BAD_REQUEST,
                "Los datos enviados no son validos", errores);
    }

    /**
     * JSON mal escrito o con un formato invalido (por ejemplo una fecha
     * que no existe o que no tiene el formato AAAA-MM-DD).
     *
     * @param ex excepcion de lectura del cuerpo de la peticion
     * @return respuesta 400 con el mensaje de error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarFormatoInvalido(
            HttpMessageNotReadableException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST,
                "Formato de datos invalido. Revise el JSON y que las fechas "
                + "tengan el formato AAAA-MM-DD y sean fechas reales", null);
    }

    /**
     * El id de la URL no es un numero (por ejemplo /usuarios/abc).
     *
     * @param ex excepcion de conversion del parametro
     * @return respuesta 400 con el mensaje de error
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> manejarTipoInvalido(
            MethodArgumentTypeMismatchException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST,
                "El parametro '" + ex.getName() + "' debe ser un numero entero", null);
    }

    /**
     * Violacion de una restriccion de la base de datos, por ejemplo
     * registrar un nombre de usuario que ya existe (campo unico).
     *
     * @param ex excepcion de integridad de datos
     * @return respuesta 409 (Conflict) con el mensaje de error
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> manejarDuplicado(
            DataIntegrityViolationException ex) {
        return construirRespuesta(HttpStatus.CONFLICT,
                "El nombre de usuario ya existe. Elija otro", null);
    }

    /**
     * Arma el cuerpo JSON de la respuesta de error.
     *
     * @param estado  codigo HTTP
     * @param mensaje mensaje general
     * @param errores errores por campo (puede ser null)
     * @return respuesta con el cuerpo armado
     */
    private ResponseEntity<Map<String, Object>> construirRespuesta(
            HttpStatus estado, String mensaje, Map<String, String> errores) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("fecha", LocalDateTime.now().toString());
        cuerpo.put("estado", estado.value());
        cuerpo.put("mensaje", mensaje);
        if (errores != null) {
            cuerpo.put("errores", errores);
        }
        return ResponseEntity.status(estado).body(cuerpo);
    }
}