package com.globant.clientrest.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;
    @Column(name = "nombre", length = 50)
    @Size(min = 1, max = 50, message = "el nombre debe tener una longitud maxima de 50 caracteres")
    @NotNull(message = "el nombre es obligatorio")
    private String nombre;
    @Column(name = "apellido", length = 50)
    @Size(min = 1, max = 50, message = "el apellido debe tener una longitud maxima de 50 caracteres")
    @NotNull(message = "el apellido es obligatorio")
    private String apellido;
    @Column(name = "razon_social", length = 150)
    @Size(min = 1, max = 150, message = "la razon social debe tener una longitud maxima de 150 caracteres")
    @NotNull(message = "la razon social es obligatorio")
    private String razonSocial;
    @Column(name = "identificador", length = 30, unique = true)
    @Size(min = 1, max = 30, message = "el identificador debe tener una longitud maxima de 30 caracteres")
    @NotNull(message = "el identificador es obligatorio")
    @Digits(message = "el identificador debe contener solo numeros", integer = 30, fraction = 0)
    private String identificador;
    @Column(name = "correo_electronico", length = 50)
    @Size(min = 1, max = 50, message = "el correo debe tener una longitud maxima de 50 caracteres")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El email no tiene la escructura correcta")
    @NotNull(message = "el correo es obligatorio")
    private String correoElectronico;
    @Column(name = "direccion", length = 150)
    @Size(min = 1, max = 150, message = "la direccion debe tener una longitud maxima de 150 caracteres")
    @NotNull(message = "la direccion es obligatorio")
    private String direccion;
    @Column(name = "telefono", length = 20)
    @Size(min = 1, max = 20, message = "el telefono debe tener una longitud maxima de 20 caracteres")
    @NotNull(message = "el telefono es obligatorio")
    @Digits(message = "el telefono debe contener solo numeros", integer = 20, fraction = 0)
    private String telefono;

    @Tolerate
    public Client() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client that = (Client) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
