package com.andres.citas_medicas.dto.Odontologo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrearOdontologoDTO {

    @NotBlank(message = "Nombre no debe estar vacio")
    private String nombre;

    @NotBlank(message = "Apellidos no debe estar vacio")
    private String apellido;

    @NotBlank(message = "Apellidos no debe estar vacio")
    private String especialidad;

    @NotBlank(message = "Campo no debe estar vacio")
    @Email(message = "Debe ingresar un correo válido")
    private String email;
}
