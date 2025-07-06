package com.andres.citas_medicas.mapper;

import com.andres.citas_medicas.dto.horario.CrearHorarioDTO;
import com.andres.citas_medicas.dto.horario.HorarioDTO;
import com.andres.citas_medicas.entity.Horario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HorarioMapper {

    @Mapping(source = "idOdontologo", target = "odontologo.id")
    Horario toEntity(CrearHorarioDTO dto);

    @Mapping(source = "odontologo.nombre", target = "nombreOdontologo")
    HorarioDTO toDTO(Horario horario);

    List<HorarioDTO> toDTOList(List<Horario> horarios);

    @Mapping(target = "id", ignore = true)
    void actualizarDesdeDTO(CrearHorarioDTO dto,@MappingTarget Horario horario);




}
