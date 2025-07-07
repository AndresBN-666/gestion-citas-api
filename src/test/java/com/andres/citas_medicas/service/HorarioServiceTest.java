package com.andres.citas_medicas.service;

import com.andres.citas_medicas.dto.horario.CrearHorarioDTO;
import com.andres.citas_medicas.dto.horario.HorarioDTO;
import com.andres.citas_medicas.entity.Horario;
import com.andres.citas_medicas.entity.Odontologo;
import com.andres.citas_medicas.enums.DiaSemana;
import com.andres.citas_medicas.mapper.HorarioMapper;
import com.andres.citas_medicas.repository.HorarioRepository;
import com.andres.citas_medicas.repository.OdontologoRepository;
import com.andres.citas_medicas.service.impl.HorarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HorarioServiceTest {

    @InjectMocks
    private HorarioServiceImpl horarioService;

    @Mock
    private HorarioRepository horarioRepository;

    @Mock
    private OdontologoRepository odontologoRepository;

    @Mock
    private HorarioMapper horarioMapper;

    @Test
    void listarTurnos(){

        Horario horario1 = new Horario();
        Horario horario2 = new Horario();
        List<Horario> listaEntidades = List.of(horario1, horario2);

        HorarioDTO h1 = new HorarioDTO();
        HorarioDTO h2 = new HorarioDTO();
        List<HorarioDTO> listDto = List.of(h1, h2);

        when(horarioRepository.findAll()).thenReturn(listaEntidades);
        when(horarioMapper.toDTOList(listaEntidades)).thenReturn(listDto);

        List<HorarioDTO> resultado = horarioService.listarTodos();

        assertEquals(2, resultado.size());
        verify(horarioRepository).findAll();
        verify(horarioMapper).toDTOList(listaEntidades);
    }


    @Test
    void listarPorOdontologos(){
        Long idOdontologo = 1L;
        Odontologo existente = new Odontologo();
        existente.setId(idOdontologo);

        Horario horario1 = new Horario();
        Horario horario2 = new Horario();
        List<Horario> listaEntidades = List.of(horario1, horario2);

        HorarioDTO h1 = new HorarioDTO();
        HorarioDTO h2 = new HorarioDTO();
        List<HorarioDTO> listDto = List.of(h1, h2);

        when(odontologoRepository.findById(idOdontologo)).thenReturn(Optional.of(existente));
        when(horarioRepository.findByOdontologoId(idOdontologo)).thenReturn(listaEntidades);
        when(horarioMapper.toDTOList(listaEntidades)).thenReturn(listDto);

        List<HorarioDTO> resultado = horarioService.listarPorOdontologo(idOdontologo);

        assertEquals(2, resultado.size());
        verify(odontologoRepository).findById(idOdontologo);
        verify(horarioRepository).findByOdontologoId(idOdontologo);


    }


    @Test
    void crearHorario(){

        CrearHorarioDTO dtoCrear = new CrearHorarioDTO(DiaSemana.MONDAY, LocalTime.of(10,7),
                LocalTime.of(17,0),1L);

        Odontologo existente = new Odontologo();
        existente.setId(1L);
        existente.setNombre("Existente");

        Horario horario = new Horario();
        horario.setOdontologo(existente);

        HorarioDTO horarioDTO = new HorarioDTO(1L,DiaSemana.MONDAY, LocalTime.of(10,7),
                LocalTime.of(17,0),existente.getNombre());

        when(odontologoRepository.findById(dtoCrear.getIdOdontologo())).thenReturn(Optional.of(existente));
        when(horarioMapper.toEntity(dtoCrear)).thenReturn(horario);
        when(horarioRepository.save(horario)).thenReturn(horario);
        when(horarioMapper.toDTO(any(Horario.class))).thenReturn(horarioDTO);

        HorarioDTO resultado = horarioService.crear(dtoCrear);

        assertEquals(1, resultado.getId());
        assertEquals("Existente",resultado.getNombreOdontologo());

    }

    @Test
    void actualizarHorario(){
        Long idHorario = 1L;

        CrearHorarioDTO dtoCrear = new CrearHorarioDTO(DiaSemana.MONDAY, LocalTime.of(10,7),
                LocalTime.of(17,0),1L);

        Horario horarioExistente = new Horario();
        horarioExistente.setId(idHorario);
        horarioExistente.setDiaSemana(DiaSemana.FRIDAY);

        Odontologo existente = new Odontologo();
        existente.setId(1L);
        existente.setNombre("Existente");

        HorarioDTO resultadoEsperado = new HorarioDTO(1L,DiaSemana.MONDAY, LocalTime.of(10,7),
                LocalTime.of(17,0),existente.getNombre());

        when(horarioRepository.findById(idHorario)).thenReturn(Optional.of(horarioExistente));
        when(odontologoRepository.findById(existente.getId())).thenReturn(Optional.of(existente));

        doAnswer(invocation -> {
            CrearHorarioDTO source = invocation.getArgument(0);
            Horario target = invocation.getArgument(1);
            target.setDiaSemana(source.getDiaSemana());
            target.setHoraInicio(source.getHoraInicio());
            target.setHoraFin(source.getHoraFin());
            return null;
        }).when(horarioMapper).actualizarDesdeDTO(dtoCrear, horarioExistente);
        when(horarioRepository.save(horarioExistente)).thenReturn(horarioExistente);
        when(horarioMapper.toDTO(horarioExistente)).thenReturn(resultadoEsperado);

        HorarioDTO resultado = horarioService.actualizar(idHorario,dtoCrear);

        assertEquals(resultadoEsperado.getId(),resultado.getId());
        assertEquals(resultadoEsperado.getNombreOdontologo(), resultado.getNombreOdontologo());


    }

    @Test
    void eliminarHorario(){
        Long idHorario = 1L;
        Horario horario = new Horario();
        horario.setId(idHorario);

        when(horarioRepository.findById(idHorario)).thenReturn(Optional.of(horario));


        horarioService.eliminar(idHorario);

        verify(horarioRepository).findById(idHorario);
    }

}
