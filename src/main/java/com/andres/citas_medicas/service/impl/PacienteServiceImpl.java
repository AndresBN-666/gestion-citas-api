package com.andres.citas_medicas.service.impl;

import com.andres.citas_medicas.dto.paciente.CrearPacienteDTO;
import com.andres.citas_medicas.dto.paciente.PacienteDTO;
import com.andres.citas_medicas.entity.Paciente;
import com.andres.citas_medicas.mapper.PacienteMapper;
import com.andres.citas_medicas.repository.PacienteRepository;
import com.andres.citas_medicas.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper mapper;


    @Override
    public List<PacienteDTO> listarTodos() {
        return mapper.toDTOList(pacienteRepository.findAll());
    }

    @Override
    public PacienteDTO listarPacientePorId(Long id) {

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        return mapper.toDTO(paciente);
    }

    @Override
    public PacienteDTO crearPaciente(CrearPacienteDTO dto) {
        Paciente entidad = mapper.toEntity(dto);
        return mapper.toDTO(pacienteRepository.save(entidad));
    }

    @Override
    public PacienteDTO actualizarPaciente(Long id, CrearPacienteDTO dto) {
        Paciente existente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("paciente no encontrado"));
        mapper.actualizarDesdeDTO(dto, existente);
        pacienteRepository.save(existente);

        return mapper.toDTO(existente);
    }

    @Override
    public void eliminarPaciente(Long id) {
        Paciente existente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("paciente no encontrado"));
        pacienteRepository.deleteById(id);
    }
}
