package com.andres.citas_medicas.mapper;

import com.andres.citas_medicas.dto.turno.CrearTurnoDTO;
import com.andres.citas_medicas.dto.turno.TurnoDTO;
import com.andres.citas_medicas.entity.Turno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TurnoMapper {

    @Mapping(source = "pacienteId", target = "paciente.id")
    @Mapping(source = "odontologoId", target = "odontologo.id")
    Turno toEntity(CrearTurnoDTO dto);


    @Mapping(source = "paciente.nombre", target = "nombrePaciente")
    @Mapping(source = "odontologo.nombre", target = "nombreOdontologo")
    TurnoDTO toDTO(Turno turno);

    List<TurnoDTO> toDTOList(List<Turno> turnos);

    //@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void actualizarDesdeDTO(CrearTurnoDTO dto, @MappingTarget Turno turno);


}
