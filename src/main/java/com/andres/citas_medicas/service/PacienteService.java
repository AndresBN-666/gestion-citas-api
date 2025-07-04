package com.andres.citas_medicas.service;

import com.andres.citas_medicas.dto.paciente.CrearPacienteDTO;
import com.andres.citas_medicas.dto.paciente.PacienteDTO;

import java.util.List;

public interface PacienteService {

    List<PacienteDTO> listarTodos();
    PacienteDTO listarPacientePorId(Long id);
    PacienteDTO crearPaciente(CrearPacienteDTO dto);
    PacienteDTO actualizarPaciente(Long id, CrearPacienteDTO dto);
    void eliminarPaciente(Long id);
}
