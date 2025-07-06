package com.andres.citas_medicas.dto.horario;

import com.andres.citas_medicas.enums.DiaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class CrearHorarioDTO {

    @NotNull(message = "Dia semana no debe estar vacio")
    private DiaSemana diaSemana;

    @NotNull(message = "Fecha de inicio no puede estar vacio")
    private LocalTime horaInicio;

    @NotNull(message = "Fecha de fin no puede estar vacio")
    private LocalTime horaFin;

    @NotNull(message = "Odontologo no debe estar vacio")
    private Long idOdontologo;
}
