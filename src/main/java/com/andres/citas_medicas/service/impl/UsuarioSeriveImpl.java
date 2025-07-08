package com.andres.citas_medicas.service.impl;

import com.andres.citas_medicas.dto.usuario.PerfilUsuarioDTO;
import com.andres.citas_medicas.dto.usuario.UsuarioDTO;
import com.andres.citas_medicas.entity.Usuario;
import com.andres.citas_medicas.mapper.UsuarioMapper;
import com.andres.citas_medicas.repository.UsuarioRepository;
import com.andres.citas_medicas.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioSeriveImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioMapper.toDtoList(usuarioRepository.findAll());
    }

    @Override
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public PerfilUsuarioDTO obtenerPerfil() {
        String nombre = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("No existe el usuario con nombre: " + nombre));

        return PerfilUsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .rol(usuario.getRol().name())
                .build();
    }
}
