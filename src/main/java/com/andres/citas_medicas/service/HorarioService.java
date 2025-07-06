package com.andres.citas_medicas.service;

import com.andres.citas_medicas.dto.horario.CrearHorarioDTO;
import com.andres.citas_medicas.dto.horario.HorarioDTO;

import java.util.List;

public interface HorarioService {

    List<HorarioDTO> listarTodos();
    List<HorarioDTO> listarPorOdontologo(Long idOdontologo);
    HorarioDTO crear (CrearHorarioDTO dto);
    HorarioDTO actualizar(Long id, CrearHorarioDTO dto);
    void eliminar(Long id);
}
