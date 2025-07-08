package com.andres.citas_medicas.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerfilUsuarioDTO {
    private Long id;
    private String nombre;
    private String rol;
}
