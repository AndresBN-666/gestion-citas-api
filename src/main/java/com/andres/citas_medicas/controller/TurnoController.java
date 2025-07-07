package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.turno.CrearTurnoDTO;
import com.andres.citas_medicas.dto.turno.TurnoDTO;
import com.andres.citas_medicas.enums.EstadoTurno;
import com.andres.citas_medicas.service.TurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Listar todos los turnos")
    @ApiResponse(responseCode = "200", description = "Lista de todos los turnos")
    @GetMapping
    public ResponseEntity<List<TurnoDTO>>listarTurnos() {
        return ResponseEntity.ok(turnoService.listarTodos());
    }


    @Operation(summary = "Crear un nuevo turno")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Turno creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en el cuerpo de la solicitud")
    })
    @PostMapping
    public ResponseEntity<TurnoDTO> crearTurno(@RequestBody @Valid CrearTurnoDTO turnoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turnoService.crearTurno(turnoDTO));
    }


    @Operation(summary = "Cancelar un turno por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Turno cancelado correctamente"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelarTurno(@PathVariable Long id) {
        turnoService.cancelarTurno(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Completar un turno por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Turno completado correctamente"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    @PutMapping("/completar/{id}")
    public ResponseEntity<Void> completarTurno(@PathVariable Long id) {
        turnoService.completarTurno(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Buscar turnos por estado")
    @ApiResponse(responseCode = "200", description = "Lista de turnos con el estado indicado")
    @GetMapping("/estado")
    public ResponseEntity<List<TurnoDTO>> buscarPorEstado(@RequestParam("estado") EstadoTurno estado) {
        return ResponseEntity.ok(turnoService.buscarPorEstado(estado));
    }

    @Operation(summary = "Buscar turnos por fecha")
    @ApiResponse(responseCode = "200", description = "Lista de turnos en la fecha especificada")
    @GetMapping("/fecha")
    public ResponseEntity<List<TurnoDTO>> buscarPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate fecha) {
        return ResponseEntity.ok(turnoService.buscarPorFecha(fecha));
    }

    @Operation(summary = "Buscar turnos por fecha y estado")
    @ApiResponse(responseCode = "200", description = "Lista de turnos que coinciden con la fecha y el estado")
    @GetMapping("/buscar")
    public ResponseEntity<List<TurnoDTO>> buscarPorFechaYEstado(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("estado") EstadoTurno estado){
        return ResponseEntity.ok(turnoService.buscarPorEstadoYfecha(fecha, estado));
    }

}
