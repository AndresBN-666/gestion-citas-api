package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.usuario.PerfilUsuarioDTO;
import com.andres.citas_medicas.dto.usuario.UsuarioDTO;
import com.andres.citas_medicas.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Listar todos los Usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de todos los Usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }


    @Operation(summary = "Obtener usuario por id")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado por Id")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> ObtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @Operation(summary = "Obtener perfil de usuario")
    @ApiResponse(responseCode = "200", description = "Perfil de usuario logueado")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/perfil")
    public ResponseEntity<PerfilUsuarioDTO> obtenerPerfil(){
        return ResponseEntity.ok(usuarioService.obtenerPerfil());
    }
}
