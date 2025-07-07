package com.andres.citas_medicas.service;

import com.andres.citas_medicas.dto.paciente.CrearPacienteDTO;
import com.andres.citas_medicas.dto.paciente.PacienteDTO;
import com.andres.citas_medicas.entity.Paciente;
import com.andres.citas_medicas.mapper.PacienteMapper;
import com.andres.citas_medicas.repository.PacienteRepository;
import com.andres.citas_medicas.service.impl.PacienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private PacienteMapper mapper;

    @InjectMocks
    private PacienteServiceImpl pacienteService;


    @Test
    void testListarPacientePorId_Exitoso() {
        Long id = 1L;
        Paciente paciente = new Paciente();
        PacienteDTO dto = new PacienteDTO();

        when(pacienteRepository.findById(id)).thenReturn(Optional.of(paciente));
        when(mapper.toDTO(paciente)).thenReturn(dto);

        PacienteDTO resultado = pacienteService.listarPacientePorId(id);

        assertNotNull(resultado);
        verify(pacienteRepository).findById(id);
    }

    @Test
    void testListarPacientePorId_NoEncontrado() {
        when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> pacienteService.listarPacientePorId(99L));

        assertEquals("Paciente no encontrado", ex.getMessage());
    }

    @Test
    void testCrearPaciente() {
        CrearPacienteDTO dto = new CrearPacienteDTO();
        Paciente entidad = new Paciente();
        PacienteDTO resultadoEsperado = new PacienteDTO();

        when(mapper.toEntity(dto)).thenReturn(entidad);
        when(pacienteRepository.save(entidad)).thenReturn(entidad);
        when(mapper.toDTO(entidad)).thenReturn(resultadoEsperado);

        PacienteDTO resultado = pacienteService.crearPaciente(dto);

        assertNotNull(resultado);
        verify(pacienteRepository).save(entidad);
    }

    @Test
    void testActualizarPaciente() {
        Long id = 1L;
        CrearPacienteDTO dto = new CrearPacienteDTO();
        Paciente existente = new Paciente();
        PacienteDTO esperado = new PacienteDTO();

        when(pacienteRepository.findById(id)).thenReturn(Optional.of(existente));

        when(pacienteRepository.save(existente)).thenReturn(existente);
        when(mapper.toDTO(existente)).thenReturn(esperado);

        PacienteDTO resultado = pacienteService.actualizarPaciente(id, dto);

        assertNotNull(resultado);
        verify(mapper).actualizarDesdeDTO(dto, existente);
    }

    @Test
    void testEliminarPaciente_Exitoso() {
        Long id = 1L;
        Paciente paciente = new Paciente();
        when(pacienteRepository.findById(id)).thenReturn(Optional.of(paciente));

        pacienteService.eliminarPaciente(id);

        verify(pacienteRepository).deleteById(id);
    }

    @Test
    void testEliminarPaciente_NoEncontrado() {
        when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> pacienteService.eliminarPaciente(99L));

        assertEquals("paciente no encontrado", ex.getMessage());
        verify(pacienteRepository, never()).deleteById(any());
    }
}
