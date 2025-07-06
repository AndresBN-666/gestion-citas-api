package com.andres.citas_medicas.service.impl;

import com.andres.citas_medicas.dto.horario.CrearHorarioDTO;
import com.andres.citas_medicas.dto.horario.HorarioDTO;
import com.andres.citas_medicas.entity.Horario;
import com.andres.citas_medicas.entity.Odontologo;
import com.andres.citas_medicas.mapper.HorarioMapper;
import com.andres.citas_medicas.repository.HorarioRepository;
import com.andres.citas_medicas.repository.OdontologoRepository;
import com.andres.citas_medicas.service.HorarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final OdontologoRepository odontologoRepository;
    private final HorarioMapper mapper;

    @Override
    public List<HorarioDTO> listarTodos() {
        List<Horario> horarios = horarioRepository.findAll();
        return mapper.toDTOList(horarios);
    }

    @Override
    public List<HorarioDTO> listarPorOdontologo(Long idOdontologo) {
        List<Horario> horarios = horarioRepository.findByOdontologoId(idOdontologo);
        return mapper.toDTOList(horarios);
    }

    @Override
    public HorarioDTO crear(CrearHorarioDTO dto) {
        Odontologo odontologo = odontologoRepository.findById(dto.getIdOdontologo())
                .orElseThrow(() -> new RuntimeException("Odontologo no encontrado"));

        Horario horario = mapper.toEntity(dto);
        horario.setOdontologo(odontologo);

        return mapper.toDTO(horarioRepository.save(horario));
    }

    @Override
    public HorarioDTO actualizar(Long id, CrearHorarioDTO dto) {
        Horario existente = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        Odontologo odontologo = odontologoRepository.findById(dto.getIdOdontologo())
                .orElseThrow(() -> new RuntimeException("Odontologo no encontrado"));

       mapper.actualizarDesdeDTO(dto, existente);
       existente.setOdontologo(odontologo);
        return mapper.toDTO(horarioRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        Horario existente = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        horarioRepository.deleteById(id);
    }
}
