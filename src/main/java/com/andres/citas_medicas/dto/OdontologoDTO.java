package com.andres.citas_medicas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OdontologoDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String email;
}
