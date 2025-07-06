package com.andres.citas_medicas.service.impl;

import com.andres.citas_medicas.dto.Odontologo.OdontologoDTO;
import com.andres.citas_medicas.entity.Odontologo;
import com.andres.citas_medicas.mapper.OdontologoMapper;
import com.andres.citas_medicas.repository.OdontologoRepository;
import com.andres.citas_medicas.service.OdontologoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OdontologoServiceImpl implements OdontologoService {

    private final OdontologoRepository odontologoRepository;
    private final OdontologoMapper odontologoMapper;


    @Override
    public OdontologoDTO crear(OdontologoDTO odontologoDTO) {
        Odontologo odontologo = odontologoMapper.toEntity(odontologoDTO);
        return odontologoMapper.toDto(odontologoRepository.save(odontologo));
    }

    @Override
    public List<OdontologoDTO> listar() {
        return odontologoMapper.toDTOList(odontologoRepository.findAll());
    }

    @Override
    public OdontologoDTO buscarPorId(Long id) {
        Odontologo odontologo = odontologoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Odontologo no encontrado"));

        return odontologoMapper.toDto(odontologo);
    }

    @Override
    public void eliminar(Long id) {
        Odontologo odontologo = odontologoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Odontologo no encontrado"));
        odontologoRepository.deleteById(id);

    }
}
