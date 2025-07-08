package com.andres.citas_medicas.auth;

import com.andres.citas_medicas.dto.registro.AuthResponseDTO;
import com.andres.citas_medicas.dto.registro.LoginDTO;
import com.andres.citas_medicas.dto.registro.RegistrarDTO;
import com.andres.citas_medicas.entity.Usuario;
import com.andres.citas_medicas.enums.Rol;
import com.andres.citas_medicas.jwt.JwtService;
import com.andres.citas_medicas.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO registrar(RegistrarDTO dto){
        if (usuarioRepository.existsByNombre(dto.getNombre())){
            throw new RuntimeException("El nombre de usuario ya esta en uso");
        }
        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .clave(passwordEncoder.encode(dto.getClave()))
                .rol(dto.getRol() !=null ? dto.getRol() : Rol.USER)
                .build();
        usuarioRepository.save(usuario);
        return new AuthResponseDTO("Usuario registrado correctamente");
    }


    public AuthResponseDTO login (LoginDTO request) {
        Usuario usuario = usuarioRepository.findByNombre(request.getNombre())
                .orElseThrow(() -> new RuntimeException("El nombre no existe"));

        if (!passwordEncoder.matches(request.getClave(), usuario.getClave())){
            throw new RuntimeException("Contraseña incorrecta");
        }
        String token = jwtService.generarToken(usuario);
        return new AuthResponseDTO(token);
    }

}
