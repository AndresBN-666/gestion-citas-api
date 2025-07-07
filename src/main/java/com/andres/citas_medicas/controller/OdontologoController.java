package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.Odontologo.CrearOdontologoDTO;
import com.andres.citas_medicas.dto.Odontologo.OdontologoDTO;
import com.andres.citas_medicas.service.OdontologoService;
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
@RequestMapping("/odontologo")
@RequiredArgsConstructor
public class OdontologoController {

    private final OdontologoService odontologoService;

    @Operation(summary = "Listar todos los Odontologos")
    @ApiResponse(responseCode = "200", description = "Lista de Odontologos obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<OdontologoDTO>> listarTodos(){
        return ResponseEntity.ok(odontologoService.listar());
    }

    @Operation(summary = "Crear un nuevo odontogologo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "odontogologo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<OdontologoDTO> crear(@RequestBody @Valid CrearOdontologoDTO odontologoDTO){
        OdontologoDTO result = odontologoService.crear(odontologoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @Operation(summary = "Buscar odontologo por su Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "odontólogo obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDTO> buscarPorId(@PathVariable Long id){
        OdontologoDTO result = odontologoService.buscarPorId(id);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Eliminar un odontologo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "odontologo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "odontologo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        odontologoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
