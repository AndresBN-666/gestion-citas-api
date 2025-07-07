package com.andres.citas_medicas.dto.paciente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private String dni;

    private String email;

    private String telefono;

}
