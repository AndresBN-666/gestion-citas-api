package com.andres.citas_medicas.dto.usuario;

import com.andres.citas_medicas.enums.Rol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;

    private String nombre;

    private String correo;

    private String clave;

    private Rol rol;
}
