package com.andres.citas_medicas.dto.turno;

import com.andres.citas_medicas.enums.EstadoTurno;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CrearTurnoDTO {

    @Future(message = "La fecha no puede ser pasada")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha No puede estar vacio")
    private LocalDate fecha;

    @NotNull(message = "La hora No puede estar vacio")
    private LocalTime hora;
    //private EstadoTurno estadoTurno;

    @NotNull(message = "Paciente No puede estar vacio")
    private Long pacienteId;
    @NotNull(message = "Odontologo No puede estar vacio")
    private Long odontologoId;
}
