package com.andres.citas_medicas.mapper;

import com.andres.citas_medicas.dto.usuario.UsuarioDTO;
import com.andres.citas_medicas.entity.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario usuario);

    List<UsuarioDTO> toDtoList(List<Usuario> usuarios);
}
