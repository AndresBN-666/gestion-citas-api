package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.Odontologo.OdontologoDTO;
import com.andres.citas_medicas.dto.paciente.CrearPacienteDTO;
import com.andres.citas_medicas.dto.paciente.PacienteDTO;
import com.andres.citas_medicas.service.PacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PacienteController.class)
public class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearPaciente() throws Exception {
        CrearPacienteDTO crearDTO = new CrearPacienteDTO("Andrés", "Neyra", "12345678", "andres@mail.com", "987654321");
        PacienteDTO respuesta = new PacienteDTO(1L,"Andrés", "Neyra", "12345678", "andres@mail.com", "987654321");

        when(pacienteService.crearPaciente(any(CrearPacienteDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/paciente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Andrés"));


    }

    @Test
    void BuscarPacientePorId() throws Exception {
        OdontologoDTO odontologo = new OdontologoDTO(1L, "Andres", "Barcena","Cirujano","andres@ejemplo.com");
        PacienteDTO dto = new PacienteDTO(1L,"Andrés", "Neyra", "12345678", "andres@mail.com", "987654321");

        when(pacienteService.listarPacientePorId(1L)).thenReturn(dto);

        mockMvc.perform(get("/paciente/1"))
                        .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Andrés"));

    }

    @Test
    void listarTodosLosPacientes() throws Exception {
        PacienteDTO paciente1 = new PacienteDTO(1L, "Andrés", "Neyra", "12345678", "andres@mail.com", "987654321");
        PacienteDTO paciente2 = new PacienteDTO(2L, "Luis", "Martínez", "87654321", "luis@mail.com", "912345678");

        when(pacienteService.listarTodos()).thenReturn(Arrays.asList(paciente1, paciente2));

        mockMvc.perform(get("/paciente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void actualizarPaciente() throws Exception{

        CrearPacienteDTO actualizarDTO = new CrearPacienteDTO("Nuevo", "Apellido", "12312312", "nuevo@mail.com", "912345678");
        PacienteDTO resultado = new PacienteDTO(1L, "Nuevo", "Apellido", "12312312", "nuevo@mail.com", "912345678");

        when(pacienteService.actualizarPaciente(any(Long.class), any(CrearPacienteDTO.class)))
                .thenReturn(resultado);

        mockMvc.perform(put("/paciente/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizarDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nuevo"))
                .andExpect(jsonPath("$.email").value("nuevo@mail.com"));


    }

    @Test
    void testEliminarPaciente() throws Exception {

        mockMvc.perform(delete("/paciente/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    void testBuscarPacienteNoExistente() throws Exception {
        when(pacienteService.listarPacientePorId(99L))
                .thenThrow(new RuntimeException("Paciente no encontrado"));

        mockMvc.perform(get("/paciente/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Paciente no encontrado"));
    }

}
