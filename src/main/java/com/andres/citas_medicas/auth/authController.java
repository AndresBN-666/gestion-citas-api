package com.andres.citas_medicas.auth;

import com.andres.citas_medicas.dto.registro.AuthResponseDTO;
import com.andres.citas_medicas.dto.registro.LoginDTO;
import com.andres.citas_medicas.dto.registro.RegistrarDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class authController {

    private final AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponseDTO> registrar(@RequestBody RegistrarDTO dto){
        return ResponseEntity.ok(authService.registrar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO dto){
        return ResponseEntity.ok(authService.login(dto));
    }
}
