package com.andres.citas_medicas.service;

import com.andres.citas_medicas.dto.Odontologo.CrearOdontologoDTO;
import com.andres.citas_medicas.dto.Odontologo.OdontologoDTO;
import com.andres.citas_medicas.entity.Odontologo;
import com.andres.citas_medicas.mapper.OdontologoMapper;
import com.andres.citas_medicas.repository.OdontologoRepository;
import com.andres.citas_medicas.service.impl.OdontologoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OdontologoServiceTest {


    @Mock
    private OdontologoRepository odontologoRepository;

    @Mock
    private OdontologoMapper odontologoMapper;

    @InjectMocks
    private OdontologoServiceImpl odontologoService;

    @Test
    void testCrear() {
        CrearOdontologoDTO dto = new CrearOdontologoDTO("Juan", "Perez", "Ortodoncia", "juan@correo.com");
        Odontologo entidad = new Odontologo();
        OdontologoDTO esperado = new OdontologoDTO();

        when(odontologoMapper.toEntity(dto)).thenReturn(entidad);
        when(odontologoRepository.save(entidad)).thenReturn(entidad);
        when(odontologoMapper.toDto(entidad)).thenReturn(esperado);

        OdontologoDTO resultado = odontologoService.crear(dto);

        assertNotNull(resultado);
        verify(odontologoRepository).save(entidad);
    }

    @Test
    void testListar() {
        List<Odontologo> entidades = List.of(new Odontologo(), new Odontologo());
        List<OdontologoDTO> dtos = List.of(new OdontologoDTO(), new OdontologoDTO());

        when(odontologoRepository.findAll()).thenReturn(entidades);
        when(odontologoMapper.toDTOList(entidades)).thenReturn(dtos);

        List<OdontologoDTO> resultado = odontologoService.listar();

        assertEquals(2, resultado.size());
        verify(odontologoRepository).findAll();
    }

    @Test
    void testBuscarPorId_Exitoso() {
        Long id = 1L;
        Odontologo entidad = new Odontologo();
        OdontologoDTO esperado = new OdontologoDTO();

        when(odontologoRepository.findById(id)).thenReturn(Optional.of(entidad));
        when(odontologoMapper.toDto(entidad)).thenReturn(esperado);

        OdontologoDTO resultado = odontologoService.buscarPorId(id);

        assertNotNull(resultado);
        verify(odontologoRepository).findById(id);
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        when(odontologoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> odontologoService.buscarPorId(1L));

        assertEquals("Odontologo no encontrado", ex.getMessage());
        verify(odontologoRepository).findById(1L);
    }

    @Test
    void testEliminar_Exitoso() {
        Long id = 1L;
        Odontologo entidad = new Odontologo();
        when(odontologoRepository.findById(id)).thenReturn(Optional.of(entidad));

        odontologoService.eliminar(id);

        verify(odontologoRepository).deleteById(id);
    }

    @Test
    void testEliminar_NoEncontrado() {
        when(odontologoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> odontologoService.eliminar(1L));

        assertEquals("Odontologo no encontrado", ex.getMessage());
        verify(odontologoRepository, never()).deleteById(any());
    }
}
