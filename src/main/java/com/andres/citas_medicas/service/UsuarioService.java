package com.andres.citas_medicas.service;

import com.andres.citas_medicas.dto.usuario.PerfilUsuarioDTO;
import com.andres.citas_medicas.dto.usuario.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO obtenerPorId(Long id);
    PerfilUsuarioDTO obtenerPerfil();
}
