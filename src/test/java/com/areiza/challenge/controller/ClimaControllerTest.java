package com.areiza.challenge.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.areiza.challenge.interfaces.IClimaService;
import com.areiza.challenge.model.DTO.ClimaConditionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ClimaControllerTest {

    @InjectMocks
    private ClimaController climaController;

    @Mock
    private IClimaService iClimaService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(climaController).build();
    }

    @Test
    public void testSetPrediction() throws Exception {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("2028-01-30", "Lluvia");
        ResponseEntity<Map<String, String>> responseEntity = ResponseEntity.ok(responseBody);
        when(iClimaService.prediction()).thenReturn(responseEntity);

        mockMvc.perform(get("/clima/prediction"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"2028-01-30\":\"Lluvia\"}"));

        verify(iClimaService, times(1)).prediction();
    }

    @Test
    public void testPredecirClima() throws Exception {
        LocalDate fecha = LocalDate.of(2024, 7, 22);
        ClimaConditionDTO climaCondition = new ClimaConditionDTO(); // Suponiendo que tienes un DTO con un constructor vacío.
        when(iClimaService.getClimaFecha(fecha)).thenReturn(climaCondition);

        mockMvc.perform(get("/clima/fecha").param("fecha", "2024-07-22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(iClimaService, times(1)).getClimaFecha(fecha);
    }

    @Test
    public void testPeriodosSequia() throws Exception {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("periodo", "sequia");
        ResponseEntity<Map<String, String>> responseEntity = ResponseEntity.ok(responseBody);
        when(iClimaService.periodosSequia()).thenReturn(responseEntity);

        mockMvc.perform(get("/clima/periodos/sequia"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"periodo\":\"sequia\"}"));

        verify(iClimaService, times(1)).periodosSequia();
    }

    @Test
    public void testPeriodosCondicionOptima() throws Exception {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("periodo", "condicion optima");
        ResponseEntity<Map<String, String>> responseEntity = ResponseEntity.ok(responseBody);
        when(iClimaService.periodosCondicionOptima()).thenReturn(responseEntity);

        mockMvc.perform(get("/clima/periodos/condicionOptima"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"periodo\":\"condicion optima\"}"));

        verify(iClimaService, times(1)).periodosCondicionOptima();
    }

    @Test
    public void testPeriodosLluvia() throws Exception {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("periodo", "lluvia");
        ResponseEntity<Map<String, String>> responseEntity = ResponseEntity.ok(responseBody);
        when(iClimaService.periodosLluvia()).thenReturn(responseEntity);

        mockMvc.perform(get("/clima/periodos/lluvia"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"periodo\":\"lluvia\"}"));

        verify(iClimaService, times(1)).periodosLluvia();
    }

    @Test
    public void testDiaMayorIntensidadLluvia() throws Exception {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("dia", "mayor intensidad de lluvia");
        ResponseEntity<Map<String, String>> responseEntity = ResponseEntity.ok(responseBody);
        when(iClimaService.diaMayorIntensidadLluvia()).thenReturn(responseEntity);

        mockMvc.perform(get("/clima/periodos/lluvia/diaPicoMax"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"dia\":\"mayor intensidad de lluvia\"}"));

        verify(iClimaService, times(1)).diaMayorIntensidadLluvia();
    }
}