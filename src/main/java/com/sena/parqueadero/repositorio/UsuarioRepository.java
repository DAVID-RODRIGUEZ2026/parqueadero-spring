package com.sena.parqueadero.repositorio;

import com.sena.parqueadero.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de acceso a datos para la entidad Usuario.
 * Reemplaza al antiguo UsuarioDAO: al extender JpaRepository,
 * Spring Data JPA genera automaticamente los metodos para listar,
 * buscar, guardar, actualizar y eliminar usuarios.
 *
 * @author Manuel David Rodriguez Garcia - SENA ficha 3336117
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}