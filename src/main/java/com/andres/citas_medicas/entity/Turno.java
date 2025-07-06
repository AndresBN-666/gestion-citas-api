package com.andres.citas_medicas.entity;

import com.andres.citas_medicas.enums.EstadoTurno;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "turno")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    private EstadoTurno estadoTurno;

    @ManyToOne
    @JoinColumn(name = "odontologo_id")
    private Odontologo odontologo;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

}
