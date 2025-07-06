package com.andres.citas_medicas.service;

import com.andres.citas_medicas.dto.turno.CrearTurnoDTO;
import com.andres.citas_medicas.dto.turno.TurnoDTO;
import com.andres.citas_medicas.enums.EstadoTurno;

import java.time.LocalDate;
import java.util.List;

public interface TurnoService {

    List<TurnoDTO> listarTodos();
    TurnoDTO crearTurno(CrearTurnoDTO dto);
    void cancelarTurno(Long id);
    void completarTurno(Long id);
    List<TurnoDTO> buscarPorEstado(EstadoTurno estado);
    List<TurnoDTO> buscarPorFecha(LocalDate fecha);
    List<TurnoDTO> buscarPorEstadoYfecha(LocalDate fecha, EstadoTurno estado);
}
