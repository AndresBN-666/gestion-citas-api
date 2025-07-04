package com.andres.citas_medicas.dto.paciente;

import lombok.Data;

@Data
public class CrearPacienteDTO {

    private String nombre;

    private String apellido;

    private String dni;

    private String email;

    private String telefono;

}
