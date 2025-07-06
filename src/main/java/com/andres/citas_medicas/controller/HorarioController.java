package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.horario.CrearHorarioDTO;
import com.andres.citas_medicas.dto.horario.HorarioDTO;
import com.andres.citas_medicas.service.HorarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horario")
@AllArgsConstructor
public class HorarioController {

    private final HorarioService horarioService;

    @GetMapping
    public ResponseEntity<List<HorarioDTO>> listaHorarios() {
        return ResponseEntity.ok(horarioService.listarTodos());
    }
    @GetMapping("/odontologo/{id}")
    public ResponseEntity<List<HorarioDTO>> listarPorOdontolgo(@PathVariable Long id) {
        return ResponseEntity.ok(horarioService.listarPorOdontologo(id));
    }

    @PostMapping
    public ResponseEntity<HorarioDTO> crearHorario(@RequestBody @Valid CrearHorarioDTO horarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(horarioService.crear(horarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioDTO> actualizarHorario(@PathVariable Long id,
                                                        @RequestBody @Valid CrearHorarioDTO horarioDTO) {
        return ResponseEntity.ok(horarioService.actualizar(id, horarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Long id) {
        horarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
