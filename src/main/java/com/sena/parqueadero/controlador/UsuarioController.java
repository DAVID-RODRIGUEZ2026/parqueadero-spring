package com.sena.parqueadero.controlador;

import com.sena.parqueadero.modelo.Usuario;
import com.sena.parqueadero.servicio.UsuarioService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST del modulo de usuarios del parqueadero.
 * Recibe las peticiones HTTP (GET, POST, PUT, DELETE) y reemplaza
 * conceptualmente a los Servlets del proyecto anterior.
 * Ruta base: http://localhost:8080/usuarios
 *
 * Las peticiones POST y PUT validan los datos con @Valid; si algun
 * dato no cumple las reglas, se responde 400 (Bad Request) con los
 * mensajes de error (ver clase ManejadorErrores).
 *
 * @author Manuel David Rodriguez Garcia - SENA ficha 3336117
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    /** Servicio con la logica de negocio de usuarios. */
    private final UsuarioService usuarioService;

    /**
     * Inyeccion de dependencias por constructor.
     *
     * @param usuarioService servicio de usuarios
     */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * GET /usuarios : lista todos los usuarios.
     *
     * @return lista de usuarios en formato JSON
     */
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    /**
     * GET /usuarios/{id} : consulta un usuario por su id.
     *
     * @param id identificador recibido en la URL
     * @return el usuario (200) o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /usuarios : registra un usuario nuevo, validando sus datos.
     *
     * @param usuario datos del usuario recibidos en JSON
     * @return el usuario guardado con su id (201 Created)
     */
    @PostMapping
    public ResponseEntity<Usuario> guardar(@Valid @RequestBody Usuario usuario) {
        Usuario guardado = usuarioService.guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    /**
     * PUT /usuarios/{id} : actualiza los datos de un usuario existente,
     * validando los nuevos datos.
     *
     * @param id      identificador del usuario a actualizar
     * @param usuario nuevos datos del usuario en JSON
     * @return el usuario actualizado (200) o 404 si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id,
            @Valid @RequestBody Usuario usuario) {
        return usuarioService.buscarPorId(id)
                .map(existente -> {
                    existente.setNombre(usuario.getNombre());
                    existente.setApellido(usuario.getApellido());
                    existente.setDocumento(usuario.getDocumento());
                    existente.setFechaNacimiento(usuario.getFechaNacimiento());
                    existente.setUsuario(usuario.getUsuario());
                    existente.setPassword(usuario.getPassword());
                    return ResponseEntity.ok(usuarioService.guardar(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /usuarios/{id} : elimina un usuario.
     *
     * @param id identificador del usuario a eliminar
     * @return 204 si se elimino o 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (usuarioService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}