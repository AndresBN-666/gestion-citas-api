package com.andres.citas_medicas.dto.turno;


import com.andres.citas_medicas.enums.EstadoTurno;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TurnoDTO {

    private Long id;

    private LocalDate fecha;

    private LocalTime hora;

    private EstadoTurno estadoTurno;

    private String nombreOdontologo;

    private String nombrePaciente;

}
