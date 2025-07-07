package com.andres.citas_medicas.controller;

import com.andres.citas_medicas.dto.Odontologo.CrearOdontologoDTO;
import com.andres.citas_medicas.dto.Odontologo.OdontologoDTO;
import com.andres.citas_medicas.service.OdontologoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OdontologoController.class)
public class OdontologoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OdontologoService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void listarTodosLosOdontologos() throws Exception {
        OdontologoDTO odontologo1 = new OdontologoDTO(1L,"Andres", "Barcena","Cirujano","andres@ejemplo.com");
        OdontologoDTO odontologo2 = new OdontologoDTO(2L , "Odontologo2", "loqui", "dentista", "dentista@ejemplo.com");

        when(service.listar()).thenReturn(Arrays.asList(odontologo1, odontologo2));

        mockMvc.perform(get("/odontologo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void crearOdontologo() throws Exception {

        CrearOdontologoDTO crear = new CrearOdontologoDTO("Andres", "Barcena","Cirujano","andres@ejemplo.com");
        OdontologoDTO resultado = new OdontologoDTO(1L,"Andres", "Barcena","Cirujano","andres@ejemplo.com");

        when(service.crear(crear)).thenReturn(resultado);

        mockMvc.perform(post("/odontologo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(crear)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Andres"));

    }

    @Test
    void buscarOdontologoPorId() throws Exception {
        OdontologoDTO odontologo = new OdontologoDTO(1L, "Andres", "Barcena","Cirujano","andres@ejemplo.com");

        when(service.buscarPorId(1L)).thenReturn(odontologo);

        mockMvc.perform(get("/odontologo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Andres"));
    }

    @Test
    void eliminarOdontologoPorId() throws Exception {
        mockMvc.perform(delete("/odontologo/1"))
                .andExpect(status().isNoContent());
    }



}
