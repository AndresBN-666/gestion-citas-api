package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.Odontologo.OdontologoDTO;
import com.andres.citas_medicas.dto.horario.CrearHorarioDTO;
import com.andres.citas_medicas.dto.horario.HorarioDTO;
import com.andres.citas_medicas.enums.DiaSemana;
import com.andres.citas_medicas.service.HorarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HorarioController.class)
public class HorarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HorarioService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void listarHorarioPorOdontologo() throws Exception {
        HorarioDTO horario1 = new HorarioDTO(1L, DiaSemana.MONDAY, LocalTime.parse("08:30"), LocalTime.parse("16:00"),"Andres");
        HorarioDTO horario2 = new HorarioDTO(2L, DiaSemana.FRIDAY,LocalTime.parse("10:00"), LocalTime.parse("18:00"),"Andres");

        when(service.listarTodos()).thenReturn(Arrays.asList(horario1,horario2));

        mockMvc.perform(get("/horario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    void listarHorariosPorOdontologo() throws Exception {
        OdontologoDTO odontologo = new OdontologoDTO(1L, "Andres", "Barcena","Cirujano","andres@ejemplo.com");

        HorarioDTO horario1 = new HorarioDTO(1L, DiaSemana.MONDAY, LocalTime.parse("08:30"), LocalTime.parse("16:00"),"Andres");
        HorarioDTO horario2 = new HorarioDTO(2L, DiaSemana.FRIDAY,LocalTime.parse("10:00"), LocalTime.parse("18:00"),"Andres");


        when(service.listarPorOdontologo(odontologo.getId())).thenReturn(Arrays.asList(horario1,horario2));

        mockMvc.perform(get("/horario/odontologo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void crearHorario() throws Exception {
        String json = """
        {
            "diaSemana": "VIERNES",
            "horaInicio": "10:05",
            "horaFin": "18:00",
            "idOdontologo": 1
        }
        """;
        HorarioDTO resultado = new HorarioDTO(1L,DiaSemana.FRIDAY, LocalTime.of(10, 5),
                LocalTime.of(18, 0), "Andres");

        when(service.crear(any(CrearHorarioDTO.class))).thenReturn(resultado);

        mockMvc.perform(post("/horario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreOdontologo").value("Andres"));
    }

    @Test
    void actualizarHorario() throws Exception {
        String json = """
        {
            "diaSemana": "Martes",
            "horaInicio": "10:05",
            "horaFin": "18:00",
            "idOdontologo": 1
        }
        """;

        HorarioDTO resultado = new HorarioDTO(1L,DiaSemana.TUESDAY, LocalTime.of(10, 5),
                LocalTime.of(18, 0), "Andres");

        when(service.actualizar(any(Long.class), any(CrearHorarioDTO.class))).thenReturn(resultado);

        mockMvc.perform(put("/horario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diaSemana").value("TUESDAY"));


    }

    @Test
    void eliminarHorario() throws Exception {
        mockMvc.perform(delete("/horario/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarHorarioPorId_Error() throws Exception {

        when(service.listarPorOdontologo(1L))
                .thenThrow(new RuntimeException("Odontologo no encontrado"));

        mockMvc.perform(get("/horario/odontologo/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje")
                        .value("Odontologo no encontrado"));
    }



}
