package com.areiza.challenge.service;

import com.areiza.challenge.controller.ClimaController;
import com.areiza.challenge.interfaces.IClimaService;
import com.areiza.challenge.model.enums.Clima;
import com.areiza.challenge.model.DTO.ClimaConditionDTO;
import com.areiza.challenge.model.Galaxia;
import com.areiza.challenge.model.builder.GalaxiaBuilder;
import com.areiza.challenge.model.entity.ClimaCondition;
import com.areiza.challenge.repository.IClimaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClimaServiceImpl implements IClimaService {

    private static final Logger logger = LoggerFactory.getLogger(ClimaController.class);

    @Autowired
    IClimaRepository iClimaRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final int YEARS_TO_ADD = 10;

    Galaxia galaxy = GalaxiaBuilder.buildGalaxia();

    @Override
    public ClimaConditionDTO getClimaFecha(LocalDate fecha) {
        ClimaCondition clima = iClimaRepository.findByFecha(fecha);
        logger.info("Metodo periodosSequia", clima);
        return modelToDTO(clima);
    }

    @Override
    public ResponseEntity<Map<String, String>> prediction() {
        logger.info("Metodo Prediction");
        Map<LocalDate, Clima> predictions = galaxy.predecirClima(LocalDate.now(), LocalDate.now().plusYears(YEARS_TO_ADD));
        iClimaRepository.deleteAll();
        savePrediction(predictions);
        Map<String, String> map = new HashMap<>();
        if (predictions.isEmpty()) {
            map.put("mensaje", "No se pudo hacer niguna prediccion");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
        } else {
            map.put("mensaje", "Hay un total de " + predictions.size() + " días");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }
    }

    private void savePrediction(Map<LocalDate, Clima> predictions){
        for (Map.Entry<LocalDate, Clima> entrada : predictions.entrySet()) {
            LocalDate fecha = entrada.getKey();
            Clima clima = entrada.getValue();
            ClimaCondition climaCondition = ClimaCondition.builder()
                    .fecha(fecha)
                    .clima(clima).build();
            iClimaRepository.save(climaCondition);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> periodosSequia() {
        logger.info("Metodo PeriodosSequia");
        List<ClimaCondition> periodosClimaDTO = iClimaRepository.findByClima(Clima.DROUGHT);

        Map<String, String> map = new HashMap<>();

        if (periodosClimaDTO.isEmpty()) {
            map.put("mensaje", "No se encontraron periodos de sequía");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
        } else {
            map.put("mensaje", "Periodos de sequía encontrados: " + periodosClimaDTO.size());
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> periodosCondicionOptima() {
        logger.info("Metodo PeriodosCondicionOptima");
        List<ClimaCondition> periodosClimaDTO = iClimaRepository.findByClima(Clima.OPTIMAL_CONDITIONS);

        Map<String, String> map = new HashMap<>();

        if (periodosClimaDTO.isEmpty()) {
            map.put("mensaje", "No se encontraron periodos de condicion optima");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
        } else {
            map.put("mensaje", "Períodos de condiciones óptimas: " + periodosClimaDTO.size());
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> periodosLluvia() {
        logger.info("Metodo PeriodosLluvia");
        List<ClimaCondition> periodosClimaDTO = iClimaRepository.findByClima(Clima.RAIN);

        Map<String, String> map = new HashMap<>();

        if (periodosClimaDTO.isEmpty()) {
            map.put("mensaje", "No se encontraron periodos de lluvia");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
        } else {
            map.put("mensaje", "Períodos de lluvia: " + periodosClimaDTO.size());
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> diaMayorIntensidadLluvia() {
        logger.info("Metodo DiaMayorIntensidadLluvia");
        Map<LocalDate, Clima> climaMap = buildMapClima();
        List<LocalDate> diasLluviaByIntensity = climaMap.entrySet().stream()
                .filter(e -> Clima.RAIN.equals(e.getValue()))
                .map(Map.Entry::getKey)
                .sorted((day1, day2) -> Double.compare(galaxy.getIntensidadLluvia(day2), galaxy.getIntensidadLluvia(day1)))
                .collect(Collectors.toList());
        return buildResponse(diasLluviaByIntensity);
    }


    private ResponseEntity<Map<String, String>> buildResponse(List<LocalDate> diasLluviaByIntensity){
        logger.info("Metodo BuildResponse");
        Map<String, String> map = new HashMap<>();
        if (diasLluviaByIntensity.isEmpty()) {
            map.put("mensaje", "No hubo días de lluvia");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
        } else {
            LocalDate diaMaximaIntensidadLluvia = diasLluviaByIntensity.get(0);
            map.put("mensaje", "Día de máxima intensidad de lluvia: Día "+ diaMaximaIntensidadLluvia);
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }
    }

    public Map<LocalDate, Clima> buildMapClima(){
        logger.info("Metodo BuildMapClima");
        List<ClimaCondition> periodosClima = iClimaRepository.findByClima(Clima.RAIN);
        Map<LocalDate, Clima> climaMap = new HashMap<>();
        for (ClimaCondition condition : periodosClima) {
            LocalDate date = condition.getFecha();
            Clima clima = condition.getClima();
            climaMap.put(date, clima);
        }
        return climaMap;
    }

    private ClimaConditionDTO modelToDTO(ClimaCondition caja){
        return modelMapper.map(caja, ClimaConditionDTO.class);
    }


}
