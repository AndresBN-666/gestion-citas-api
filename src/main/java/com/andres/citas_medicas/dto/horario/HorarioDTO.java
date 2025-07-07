package com.andres.citas_medicas.dto.horario;

import com.andres.citas_medicas.enums.DiaSemana;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDTO {

    private Long id;

    private DiaSemana diaSemana;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private String nombreOdontologo;

}
