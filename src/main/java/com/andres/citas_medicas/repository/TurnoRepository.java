package com.andres.citas_medicas.repository;

import com.andres.citas_medicas.dto.turno.TurnoDTO;
import com.andres.citas_medicas.entity.Turno;
import com.andres.citas_medicas.enums.EstadoTurno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    boolean existsByOdontologoIdAndFechaAndHora(Long odontologoId, LocalDate fecha, LocalTime hora);
    List<Turno> findByEstadoTurno(EstadoTurno estadoTurno);
    List<Turno> findByFecha(LocalDate fecha);
    List<Turno> findByFechaAndEstadoTurno(LocalDate fecha, EstadoTurno estadoTurno);
}
