package com.sena.parqueadero.servicio;

import com.sena.parqueadero.modelo.Usuario;
import com.sena.parqueadero.repositorio.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Capa de servicio del modulo de usuarios.
 * Contiene la logica de negocio y se comunica con el repositorio.
 *
 * @author Manuel David Rodriguez Garcia - SENA ficha 3336117
 */
@Service
public class UsuarioService {

    /** Repositorio para acceder a la tabla usuario. */
    private final UsuarioRepository usuarioRepository;

    /**
     * Inyeccion de dependencias por constructor.
     *
     * @param usuarioRepository repositorio de usuarios
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Lista todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return el usuario si existe, o vacio si no existe
     */
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Guarda un usuario nuevo o actualiza uno existente.
     *
     * @param usuario usuario a guardar
     * @return el usuario guardado
     */
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id identificador del usuario a eliminar
     */
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }
}