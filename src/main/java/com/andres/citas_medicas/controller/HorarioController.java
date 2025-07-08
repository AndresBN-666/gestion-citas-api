package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.horario.CrearHorarioDTO;
import com.andres.citas_medicas.dto.horario.HorarioDTO;
import com.andres.citas_medicas.service.HorarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horario")
@AllArgsConstructor
public class HorarioController {

    private final HorarioService horarioService;

    @Operation(summary = "Listar todos los horarios")
    @ApiResponse(responseCode = "200", description = "Lista de horarios obtenida correctamente")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<HorarioDTO>> listaHorarios() {
        return ResponseEntity.ok(horarioService.listarTodos());
    }


    @Operation(summary = "Lista los horarios por Odontólogo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Horarios del odontólogo obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/odontologo/{id}")
    public ResponseEntity<List<HorarioDTO>> listarPorOdontolgo(@PathVariable Long id) {
        return ResponseEntity.ok(horarioService.listarPorOdontologo(id));
    }

    @Operation(summary = "Crear un nuevo horario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Horario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HorarioDTO> crearHorario(@RequestBody @Valid CrearHorarioDTO horarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(horarioService.crear(horarioDTO));
    }


    @Operation(summary = "Actualizar horario existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Horario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HorarioDTO> actualizarHorario(@PathVariable Long id,
                                                        @RequestBody @Valid CrearHorarioDTO horarioDTO) {
        return ResponseEntity.ok(horarioService.actualizar(id, horarioDTO));
    }

    @Operation(summary = "Eliminar un horario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Horario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Long id) {
        horarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
