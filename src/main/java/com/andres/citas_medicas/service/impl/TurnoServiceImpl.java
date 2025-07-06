package com.andres.citas_medicas.service.impl;

import com.andres.citas_medicas.dto.turno.CrearTurnoDTO;
import com.andres.citas_medicas.dto.turno.TurnoDTO;
import com.andres.citas_medicas.entity.Horario;
import com.andres.citas_medicas.entity.Odontologo;
import com.andres.citas_medicas.entity.Paciente;
import com.andres.citas_medicas.entity.Turno;
import com.andres.citas_medicas.enums.DiaSemana;
import com.andres.citas_medicas.enums.EstadoTurno;
import com.andres.citas_medicas.mapper.TurnoMapper;
import com.andres.citas_medicas.repository.HorarioRepository;
import com.andres.citas_medicas.repository.OdontologoRepository;
import com.andres.citas_medicas.repository.PacienteRepository;
import com.andres.citas_medicas.repository.TurnoRepository;
import com.andres.citas_medicas.service.PacienteService;
import com.andres.citas_medicas.service.TurnoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;
    private final PacienteRepository pacienteRepository;
    private final OdontologoRepository odontologoRepository;
    private final HorarioRepository horarioRepository;
    private final TurnoMapper mapper;

    @Override
    public List<TurnoDTO> listarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        return mapper.toDTOList(turnos);
    }

    @Override
    public TurnoDTO crearTurno(CrearTurnoDTO dto) {

        boolean existe = turnoRepository.existsByOdontologoIdAndFechaAndHora(
                dto.getOdontologoId(), dto.getFecha(), dto.getHora());

        if(existe){
            throw new RuntimeException("Ya existe un turno para ese odontólogo en esa fecha y hora.");
        }

        DiaSemana diaSemana = DiaSemana.valueOf(dto.getFecha().getDayOfWeek().name());
        List<Horario> horarios = horarioRepository.findByOdontologoId(dto.getOdontologoId());
        boolean dentroDelHorario= false;
        for (Horario horario : horarios) {
            if (horario.getDiaSemana() == diaSemana &&
                    !dto.getHora().isBefore(horario.getHoraInicio()) &&
                    !dto.getHora().isAfter(horario.getHoraFin())) {
                dentroDelHorario = true;
                break;
            }
        }

        if (!dentroDelHorario) {
            throw new RuntimeException("El odontólogo no tiene horario disponible ese día y hora.");
        }

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Odontologo odontologo = odontologoRepository.findById(dto.getOdontologoId())
                .orElseThrow(() -> new RuntimeException("Odontologo no encontrado"));



        Turno turno = mapper.toEntity(dto);
        turno.setEstadoTurno(EstadoTurno.PENDIENTE);
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        return mapper.toDTO(turnoRepository.save(turno));
    }

    @Override
    public void cancelarTurno(Long id) {
        Turno turnoExistente = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        turnoExistente.setEstadoTurno(EstadoTurno.CANCELADO);
        turnoRepository.save(turnoExistente);

    }

    @Override
    public void completarTurno(Long id) {
        Turno turnoExistente = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        turnoExistente.setEstadoTurno(EstadoTurno.COMPLETADO);
        turnoRepository.save(turnoExistente);
    }

    @Override
    public List<TurnoDTO> buscarPorEstado(EstadoTurno estado) {
        List<Turno> turnos = turnoRepository.findByEstadoTurno(estado);
        return mapper.toDTOList(turnos);
    }

    @Override
    public List<TurnoDTO> buscarPorFecha(LocalDate fecha) {
        List<Turno> turnos = turnoRepository.findByFecha(fecha);
        return mapper.toDTOList(turnos);
    }

    @Override
    public List<TurnoDTO> buscarPorEstadoYfecha(LocalDate fecha, EstadoTurno estado) {
        List<Turno> tunos = turnoRepository.findByFechaAndEstadoTurno(fecha, estado);
        return mapper.toDTOList(tunos);
    }
}
