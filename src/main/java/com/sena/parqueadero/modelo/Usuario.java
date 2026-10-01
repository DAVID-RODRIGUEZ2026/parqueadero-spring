package com.sena.parqueadero.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa la tabla "usuario" de la base de datos
 * parqueadero_spring. Cada objeto Usuario corresponde a un registro
 * de la tabla (mapeo objeto-relacional con Hibernate).
 *
 * @author Manuel David Rodriguez Garcia - SENA ficha 3336117
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    /** Identificador unico, autoincremental en MySQL. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nombre del usuario. */
    @Column(nullable = false, length = 50)
    private String nombre;

    /** Apellido del usuario. */
    @Column(nullable = false, length = 50)
    private String apellido;

    /** Nombre de usuario para iniciar sesion (no se puede repetir). */
    @Column(nullable = false, length = 50, unique = true)
    private String usuario;

    /** Contrasena del usuario. */
    @Column(nullable = false, length = 255)
    private String password;

    /** Constructor vacio requerido por JPA. */
    public Usuario() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id       identificador del usuario
     * @param nombre   nombre del usuario
     * @param apellido apellido del usuario
     * @param usuario  nombre de usuario para el login
     * @param password contrasena del usuario
     */
    public Usuario(Integer id, String nombre, String apellido,
            String usuario, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.password = password;
    }

    // ===== Metodos get y set (encapsulamiento) =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}