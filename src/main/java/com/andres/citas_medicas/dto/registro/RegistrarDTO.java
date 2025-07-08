package com.andres.citas_medicas.dto.registro;

import com.andres.citas_medicas.enums.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarDTO {

    @NotBlank(message = "El campo no debe estar vacio")
    private String nombre;
    @Email(message = "ingresar formato valido")
    private String correo;
    @NotBlank(message = "El campo no debe estar vacio")
    private String clave;

    private Rol rol;
}
