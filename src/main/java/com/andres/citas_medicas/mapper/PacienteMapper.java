package com.andres.citas_medicas.mapper;

import com.andres.citas_medicas.dto.paciente.CrearPacienteDTO;
import com.andres.citas_medicas.dto.paciente.PacienteDTO;
import com.andres.citas_medicas.entity.Paciente;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    Paciente toEntity(CrearPacienteDTO crearPacienteDTO);

    PacienteDTO toDTO(Paciente paciente);

    List<PacienteDTO> toDTOList(List<Paciente> pacientes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void actualizarDesdeDTO(CrearPacienteDTO dto, @MappingTarget Paciente paciente);

}
