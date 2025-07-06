package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.Odontologo.OdontologoDTO;
import com.andres.citas_medicas.service.OdontologoService;
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

    @GetMapping
    public ResponseEntity<List<OdontologoDTO>> listarTodos(){
        return ResponseEntity.ok(odontologoService.listar());
    }

    @PostMapping
    public ResponseEntity<OdontologoDTO> crear(@RequestBody @Valid OdontologoDTO odontologoDTO){
        OdontologoDTO result = odontologoService.crear(odontologoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDTO> buscarPorId(@PathVariable Long id){
        OdontologoDTO result = odontologoService.buscarPorId(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        odontologoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
