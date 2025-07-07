package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.turno.CrearTurnoDTO;
import com.andres.citas_medicas.dto.turno.TurnoDTO;
import com.andres.citas_medicas.enums.EstadoTurno;
import com.andres.citas_medicas.service.TurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TurnoController.class)
public class TurnoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TurnoService turnoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarTodosLosTurnos() throws Exception {
        TurnoDTO tuno1 = new TurnoDTO(1L, LocalDate.of(2025, 7, 10),
                LocalTime.of(15, 0), EstadoTurno.PENDIENTE, "andres",
                "jesus");

        TurnoDTO tuno2 = new TurnoDTO(2L, LocalDate.of(2025, 7, 11),
                LocalTime.of(13, 0), EstadoTurno.PENDIENTE, "andres",
                "conny");

        when(turnoService.listarTodos()).thenReturn(Arrays.asList(tuno1, tuno2));

        mockMvc.perform(get("/turno"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void crearTurno() throws Exception {

        String json = """
                {
                  "fecha": "2025-07-10",
                  "hora": "16:00",
                  "pacienteId": 2,
                  "odontologoId": 2
                }
                             """;

        TurnoDTO resultado = new TurnoDTO(1L, LocalDate.of(2025, 7, 10),
                LocalTime.of(16, 0), EstadoTurno.PENDIENTE, "Andres", "Jesus");

        when(turnoService.crearTurno(any(CrearTurnoDTO.class))).thenReturn(resultado);

        mockMvc.perform(post("/turno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fecha").value("2025-07-10"))
                .andExpect(jsonPath("$.hora").value("16:00:00"));

    }

    @Test
    void cancelarTurno() throws Exception {

        mockMvc.perform(put("/turno/cancelar/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    void completarTurno() throws Exception {

        mockMvc.perform(put("/turno/completar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorFecha() throws Exception {

        LocalDate fecha = LocalDate.of(2025,7,10);

        List<TurnoDTO> listaTurnos = List.of(
                new TurnoDTO(1L, fecha, LocalTime.of(10, 0), EstadoTurno.PENDIENTE,
                         "Dra. Pérez", "Andres"),
                new TurnoDTO(2L, fecha, LocalTime.of(11, 30), EstadoTurno.PENDIENTE,
                        "Dr. Gómez", "Conny")
        );

        when(turnoService.buscarPorFecha(fecha)).thenReturn(listaTurnos);

        mockMvc.perform(get("/turno/fecha")
                .param("fecha", fecha.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nombrePaciente").value("Andres"))
                .andExpect(jsonPath("$[1].nombrePaciente").value("Conny"));


    }

    @Test
    void buscarPorFechaYEstado() throws Exception {
        LocalDate fecha = LocalDate.of(2025,7,10);
        EstadoTurno estado = EstadoTurno.PENDIENTE;

        List<TurnoDTO> listaTurnos = List.of(
                new TurnoDTO(1L, fecha, LocalTime.of(10, 0), estado,
                        "Dra. Pérez", "Andres"),
                new TurnoDTO(2L, fecha, LocalTime.of(11, 30), estado,
                        "Dr. Gómez", "Conny")
        );

        when(turnoService.buscarPorEstadoYfecha(any(LocalDate.class), any(EstadoTurno.class))).thenReturn(listaTurnos);

        mockMvc.perform(get("/turno/buscar")
                .param("fecha", fecha.toString())
                .param("estado", estado.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nombrePaciente").value("Andres"));


    }
}
