package com.andres.citas_medicas.dto.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearPacienteDTO {

    @NotBlank(message = "Campo no debe estar vacio")
    private String nombre;

    @NotBlank(message = "Campo no debe estar vacio")
    private String apellido;

    @NotBlank(message = "Campo no debe estar vacio")
    @Size(min = 8, max = 8, message = "El campo debe tener exactamente 8 caracteres")
    private String dni;

    @NotBlank(message = "Campo no debe estar vacio")
    @Email(message = "Debe ingresar un correo válido")
    private String email;

    @NotBlank(message = "Campo no debe estar vacio")
    private String telefono;

}
