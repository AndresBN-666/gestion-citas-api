package com.andres.citas_medicas.service;

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
import com.andres.citas_medicas.service.impl.TurnoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TurnoServiceTest {

    @InjectMocks
    private TurnoServiceImpl turnoService;

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private HorarioRepository horarioRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private OdontologoRepository odontologoRepository;

    @Mock
    private TurnoMapper mapper;


    @Test
    void crearTurno_Correctamente() {

        CrearTurnoDTO dto = new CrearTurnoDTO(LocalDate.of(2025, 7, 11),
                LocalTime.of(10, 0), 1L, 1L);

        when(turnoRepository.existsByOdontologoIdAndFechaAndHora(anyLong(),any(LocalDate.class),
                any(LocalTime.class))).thenReturn(false);

        Horario horario = new Horario();
        horario.setDiaSemana(DiaSemana.FRIDAY);
        horario.setHoraInicio(LocalTime.of(9, 0));
        horario.setHoraFin(LocalTime.of(12, 0));

        when(horarioRepository.findByOdontologoId(1L)).thenReturn(List.of(horario));

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        Odontologo odontologo = new Odontologo();
        odontologo.setId(1L);
        odontologo.setNombre("Andres");

        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));

        Turno entidad = new Turno();
        when(mapper.toEntity(dto)).thenReturn(entidad);

        TurnoDTO turnoDTO = new TurnoDTO(1L, dto.getFecha(), dto.getHora(), EstadoTurno.PENDIENTE,
                "Pedro", odontologo.getNombre());
        when(mapper.toDTO(entidad)).thenReturn(turnoDTO);

        when(turnoRepository.save(any(Turno.class))).thenReturn(entidad);

        TurnoDTO resultado = turnoService.crearTurno(dto);

        // Verificamos resultado
        assertEquals(1L, resultado.getId());
        assertEquals("Pedro", resultado.getNombreOdontologo());



    }

    @Test
    void listarTodosLosTurnos(){

        Turno turno1 = new Turno();
        Turno turno2 = new Turno();
        List<Turno> listaTurnos = Arrays.asList(turno1, turno2);
        
        TurnoDTO dto1 = new TurnoDTO();
        TurnoDTO dto2 = new TurnoDTO();
        List<TurnoDTO> listaDatos =Arrays.asList(dto1, dto2);

        when(turnoRepository.findAll()).thenReturn(listaTurnos);
        when(mapper.toDTOList(listaTurnos)).thenReturn(listaDatos);

        List<TurnoDTO> resultado = turnoService.listarTodos();

        assertEquals(2, resultado.size());
        verify(turnoRepository).findAll();
        verify(mapper).toDTOList(listaTurnos);

    }

    @Test
    void cancelarTurno_Correcto(){
        Long id = 1L;
        Turno turnoExistente = new Turno();
        turnoExistente.setId(id);
        turnoExistente.setEstadoTurno(EstadoTurno.PENDIENTE);

        when(turnoRepository.findById(id)).thenReturn(Optional.of(turnoExistente));

        turnoService.cancelarTurno(id);

        assertEquals(EstadoTurno.CANCELADO, turnoExistente.getEstadoTurno());
        verify(turnoRepository).save(turnoExistente);
    }

    @Test
    void completarTurno_Correcto(){
        Long turnoId = 1L;
        Turno turnoExistente = new Turno();
        turnoExistente.setId(turnoId);
        turnoExistente.setEstadoTurno(EstadoTurno.PENDIENTE);

        when(turnoRepository.findById(turnoId)).thenReturn(Optional.of(turnoExistente));

        turnoService.completarTurno(turnoId);

        assertEquals(EstadoTurno.COMPLETADO, turnoExistente.getEstadoTurno());
        verify(turnoRepository).save(turnoExistente);
    }

    @Test
    void buscarPorEstado(){
        LocalDate fecha = LocalDate.of(2025, 7, 11);
        EstadoTurno estado = EstadoTurno.PENDIENTE;

        List<Turno> turnos = List.of(new Turno());
        List<TurnoDTO> dtosEsperados = List.of(new TurnoDTO());

        when(turnoRepository.findByFechaAndEstadoTurno(fecha, estado)).thenReturn(turnos);
        when(mapper.toDTOList(turnos)).thenReturn(dtosEsperados);

        List<TurnoDTO> resultado = turnoService.buscarPorEstadoYfecha(fecha, estado);

        assertEquals(dtosEsperados, resultado);
        verify(turnoRepository).findByFechaAndEstadoTurno(fecha, estado);
        verify(mapper).toDTOList(turnos);
    }




}
