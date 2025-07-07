package com.andres.citas_medicas.mapper;

import com.andres.citas_medicas.dto.Odontologo.CrearOdontologoDTO;
import com.andres.citas_medicas.dto.Odontologo.OdontologoDTO;
import com.andres.citas_medicas.entity.Odontologo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OdontologoMapper {

    OdontologoDTO toDto(Odontologo odontologo);

    Odontologo toEntity(CrearOdontologoDTO dto);

    List<OdontologoDTO> toDTOList(List<Odontologo> listaOdontologo);
}
