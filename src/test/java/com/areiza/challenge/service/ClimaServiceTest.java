package com.areiza.challenge.service;

import com.areiza.challenge.model.enums.Clima;
import com.areiza.challenge.model.DTO.ClimaConditionDTO;
import com.areiza.challenge.model.Galaxia;
import com.areiza.challenge.model.entity.ClimaCondition;
import com.areiza.challenge.repository.IClimaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClimaServiceTest {

    @InjectMocks
    ClimaServiceImpl climaService;

    @Mock
    IClimaRepository iClimaRepository;

    @Mock
    ModelMapper modelMapper;

    Galaxia galaxy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        galaxy = mock(Galaxia.class);
        climaService.galaxy = galaxy;
    }

    @Test
    void testGetClimaFecha() {
        LocalDate fecha = LocalDate.of(2024, 7, 22);
        ClimaCondition climaCondition = new ClimaCondition();
        climaCondition.setFecha(fecha);
        climaCondition.setClima(Clima.RAIN);

        when(iClimaRepository.findByFecha(fecha)).thenReturn(climaCondition);
        when(modelMapper.map(climaCondition, ClimaConditionDTO.class)).thenReturn(new ClimaConditionDTO());

        ClimaConditionDTO result = climaService.getClimaFecha(fecha);

        assertNotNull(result);
        verify(iClimaRepository).findByFecha(fecha);
        verify(modelMapper).map(climaCondition, ClimaConditionDTO.class);
    }

    @Test
    void testPrediction() {
        Map<LocalDate, Clima> predictions = new HashMap<>();
        predictions.put(LocalDate.of(2024, 7, 22), Clima.RAIN);

        when(galaxy.predecirClima(any(LocalDate.class), any(LocalDate.class))).thenReturn(predictions);

        ResponseEntity<Map<String, String>> responseEntity = climaService.prediction();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Hay un total de 1 días", responseEntity.getBody().get("mensaje"));
        verify(iClimaRepository).deleteAll();
        verify(iClimaRepository, times(1)).save(any(ClimaCondition.class));
    }

    @Test
    void testPeriodosSequia() {
        List<ClimaCondition> sequiaList = new ArrayList<>();
        sequiaList.add(new ClimaCondition());

        when(iClimaRepository.findByClima(Clima.DROUGHT)).thenReturn(sequiaList);

        ResponseEntity<Map<String, String>> responseEntity = climaService.periodosSequia();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Periodos de sequía encontrados: 1", responseEntity.getBody().get("mensaje"));
    }

    @Test
    void testPeriodosCondicionOptima() {
        List<ClimaCondition> optimaList = new ArrayList<>();
        optimaList.add(new ClimaCondition());

        when(iClimaRepository.findByClima(Clima.OPTIMAL_CONDITIONS)).thenReturn(optimaList);

        ResponseEntity<Map<String, String>> responseEntity = climaService.periodosCondicionOptima();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Períodos de condiciones óptimas: 1", responseEntity.getBody().get("mensaje"));
    }

    @Test
    void testPeriodosLluvia() {
        List<ClimaCondition> lluviaList = new ArrayList<>();
        lluviaList.add(new ClimaCondition());

        when(iClimaRepository.findByClima(Clima.RAIN)).thenReturn(lluviaList);

        ResponseEntity<Map<String, String>> responseEntity = climaService.periodosLluvia();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Períodos de lluvia: 1", responseEntity.getBody().get("mensaje"));
    }

    @Test
    void testDiaMayorIntensidadLluvia() {
        ClimaCondition condition = new ClimaCondition();
        condition.setFecha(LocalDate.of(2024, 7, 22));
        condition.setClima(Clima.RAIN);
        List<ClimaCondition> lluviaList = Collections.singletonList(condition);

        when(iClimaRepository.findByClima(Clima.RAIN)).thenReturn(lluviaList);

        // Usar lenient para permitir el stubbing aunque podría no ser utilizado
        Mockito.lenient().when(galaxy.getIntensidadLluvia(LocalDate.of(2024, 7, 22))).thenReturn(100.0);

        ResponseEntity<Map<String, String>> responseEntity = climaService.diaMayorIntensidadLluvia();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Día de máxima intensidad de lluvia: Día 2024-07-22", responseEntity.getBody().get("mensaje"));
    }
}