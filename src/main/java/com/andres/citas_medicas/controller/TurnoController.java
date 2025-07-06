package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.turno.CrearTurnoDTO;
import com.andres.citas_medicas.dto.turno.TurnoDTO;
import com.andres.citas_medicas.enums.EstadoTurno;
import com.andres.citas_medicas.service.TurnoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/turno")
@AllArgsConstructor
public class TurnoController {
    private final TurnoService turnoService;

    @GetMapping
    public ResponseEntity<List<TurnoDTO>>listarTurnos() {
        return ResponseEntity.ok(turnoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> crearTurno(@RequestBody @Valid CrearTurnoDTO turnoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turnoService.crearTurno(turnoDTO));
    }


    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelarTurno(@PathVariable Long id) {
        turnoService.cancelarTurno(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/completar/{id}")
    public ResponseEntity<Void> completarTurno(@PathVariable Long id) {
        turnoService.completarTurno(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado")
    public ResponseEntity<List<TurnoDTO>> buscarPorEstado(@RequestParam("estado") EstadoTurno estado) {
        return ResponseEntity.ok(turnoService.buscarPorEstado(estado));
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<TurnoDTO>> buscarPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate fecha) {
        return ResponseEntity.ok(turnoService.buscarPorFecha(fecha));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TurnoDTO>> buscarPorFechaYEstado(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("estado") EstadoTurno estado){
        return ResponseEntity.ok(turnoService.buscarPorEstadoYfecha(fecha, estado));
    }

}
