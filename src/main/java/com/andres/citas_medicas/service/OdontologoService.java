package com.andres.citas_medicas.service;

import com.andres.citas_medicas.dto.OdontologoDTO;

import java.util.List;
import java.util.Optional;

public interface OdontologoService {

    OdontologoDTO crear(OdontologoDTO odontologoDTO);
    List<OdontologoDTO> listar();
    OdontologoDTO buscarPorId(Long id);
    void eliminar(Long id);
}
