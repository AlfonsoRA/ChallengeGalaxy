package com.areiza.challenge.interfaces;

import com.areiza.challenge.model.DTO.ClimaConditionDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;

public interface IClimaService {

    ResponseEntity<Map<String, String>> prediction();
    ClimaConditionDTO getClimaFecha(LocalDate fecha);
    ResponseEntity<Map<String, String>> periodosSequia();
    ResponseEntity<Map<String, String>> periodosCondicionOptima();
    ResponseEntity<Map<String, String>> periodosLluvia();
    ResponseEntity<Map<String, String>> diaMayorIntensidadLluvia();

}
