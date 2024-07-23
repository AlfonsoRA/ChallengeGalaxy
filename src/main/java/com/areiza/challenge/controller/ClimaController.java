package com.areiza.challenge.controller;

import com.areiza.challenge.interfaces.IClimaService;
import com.areiza.challenge.model.DTO.ClimaConditionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping ("/clima")
public class ClimaController {

    @Autowired
    private IClimaService iClimaService;

    private static final Logger logger = LoggerFactory.getLogger(ClimaController.class);

    @GetMapping("/prediction")
    public ResponseEntity<Map<String, String>> setPrediction() {
        logger.info("Metodo Prediccion");
        return iClimaService.prediction();
    }

    @GetMapping("/fecha")
    public ClimaConditionDTO predecirClima(@RequestParam LocalDate fecha) {
        logger.info("Metodo PredecirClima, fecha: {}", fecha);
        return iClimaService.getClimaFecha(fecha);
    }

    @GetMapping("/periodos/sequia")
    public ResponseEntity<Map<String, String>> periodosSequia() {
        logger.info("Metodo PeriodosSequia");
        return iClimaService.periodosSequia();
    }

    @GetMapping("/periodos/condicionOptima")
    public ResponseEntity<Map<String, String>> periodosCondicionOptima() {
        logger.info("Metodo PeriodosCondicionOptima");
        return iClimaService.periodosCondicionOptima();
    }

    @GetMapping("/periodos/lluvia")
    public ResponseEntity<Map<String, String>> periodosLluvia() {
        logger.info("Metodo PeriodosLluvia");
        return iClimaService.periodosLluvia();
    }

    @GetMapping("/periodos/lluvia/diaPicoMax")
    public ResponseEntity<Map<String, String>> diaMayorIntensidadLluvia() {
        logger.info("Metodo DiaMayorIntensidadLluvia");
        return iClimaService.diaMayorIntensidadLluvia();
    }

}
