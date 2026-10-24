package com.sena.parqueadero.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Entidad JPA que representa la tabla "usuario" de la base de datos
 * parqueadero_spring. Cada objeto Usuario corresponde a un registro
 * de la tabla (mapeo objeto-relacional con Hibernate).
 *
 * Incluye validaciones con Bean Validation (Jakarta Validation) para
 * textos, numeros, fechas, caracteres especiales y longitudes.
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

    /** Nombre del usuario: solo letras y espacios, entre 2 y 50 caracteres. */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$",
            message = "El nombre solo puede contener letras y espacios (sin numeros ni caracteres especiales)")
    @Column(nullable = false, length = 50)
    private String nombre;

    /** Apellido del usuario: solo letras y espacios, entre 2 y 50 caracteres. */
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$",
            message = "El apellido solo puede contener letras y espacios (sin numeros ni caracteres especiales)")
    @Column(nullable = false, length = 50)
    private String apellido;

    /** Numero de documento: solo numeros, entre 6 y 10 digitos. */
    @NotBlank(message = "El documento es obligatorio")
    @Pattern(regexp = "^[0-9]{6,10}$",
            message = "El documento debe contener solo numeros, entre 6 y 10 digitos")
    @Column(length = 10)
    private String documento;

    /** Fecha de nacimiento: obligatoria y debe ser una fecha pasada. */
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    /** Nombre de usuario para iniciar sesion: letras, numeros y guion bajo, entre 4 y 20 caracteres. */
    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 4, max = 20, message = "El usuario debe tener entre 4 y 20 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9_]+$",
            message = "El usuario solo puede tener letras, numeros y guion bajo (_), sin espacios")
    @Column(nullable = false, length = 50, unique = true)
    private String usuario;

    /** Contrasena: entre 8 y 30 caracteres, con al menos una letra y un numero. */
    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 8, max = 30, message = "La contrasena debe tener entre 8 y 30 caracteres")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9]).+$",
            message = "La contrasena debe tener al menos una letra y un numero")
    @Column(nullable = false, length = 255)
    private String password;

    /** Constructor vacio requerido por JPA. */
    public Usuario() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id              identificador del usuario
     * @param nombre          nombre del usuario
     * @param apellido        apellido del usuario
     * @param documento       numero de documento
     * @param fechaNacimiento fecha de nacimiento
     * @param usuario         nombre de usuario para el login
     * @param password        contrasena del usuario
     */
    public Usuario(Integer id, String nombre, String apellido, String documento,
            LocalDate fechaNacimiento, String usuario, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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