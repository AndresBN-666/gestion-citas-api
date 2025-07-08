package com.andres.citas_medicas.dto.registro;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Campo no debe estar vacio")
    private String nombre;
    @NotBlank(message = "Campo no debe estar vacio")
    private String clave;
}
