package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.paciente.CrearPacienteDTO;
import com.andres.citas_medicas.dto.paciente.PacienteDTO;
import com.andres.citas_medicas.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {
    private final PacienteService pacienteService;

    @Operation(summary = "Listar todos los pacientes")
    @ApiResponse(responseCode = "200", description = "Listado completo de pacientes")
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarTodos(){
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @Operation(summary = "Buscar paciente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Long id){
        PacienteDTO resultado = pacienteService.listarPacientePorId(id);
        return ResponseEntity.ok(resultado);
    }


    @Operation(summary = "Crear nuevo paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<PacienteDTO> crearPaciente(@RequestBody @Valid CrearPacienteDTO dto){
        PacienteDTO resultado = pacienteService.crearPaciente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @Operation(summary = "Actualizar un paciente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizarPaciente(@PathVariable Long id,
                                                          @RequestBody @Valid CrearPacienteDTO dto){
        PacienteDTO resultado = pacienteService.actualizarPaciente(id,dto);
        return ResponseEntity.ok(resultado);
    }


    @Operation(summary = "Eliminar paciente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Paciente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id){
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }



}
