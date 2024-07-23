package com.areiza.challenge.model;

import com.areiza.challenge.model.enums.Clima;
import com.areiza.challenge.model.geometric.Alineados;
import com.areiza.challenge.model.geometric.Triangulo;
import com.areiza.challenge.repository.IClimaRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class Galaxia {
    private static final Logger logger = LoggerFactory.getLogger(Galaxia.class);

    private PlanetasPosiciones posicionSol = new PlanetasPosiciones(0, 0);

    @Autowired
    IClimaRepository iClimaRepository;

    private Planeta vulcano;
    private Planeta betasoide;
    private Planeta ferengi;

    public Galaxia(Planeta ferengi, Planeta betasoide, Planeta vulcano) {
        if (ferengi == null && betasoide == null & vulcano == null) throw new IllegalArgumentException("Los planetas no deben ser null.");

        this.ferengi = ferengi;
        this.betasoide = betasoide;
        this.vulcano = vulcano;
    }

    public Map<LocalDate, Clima> predecirClima(LocalDate dayFrom, LocalDate dayTo) {
        if (dayFrom == null && dayTo == null) throw new IllegalArgumentException("Se requieren días desde y hasta");
        if (!dayTo.isAfter(dayFrom)) throw new IllegalArgumentException("El día desde no puede ser posterior al día hasta");

        return dayFrom.datesUntil(dayTo.plusDays(1))
                .collect(Collectors.toMap(
                        Function.identity(),
                        this::predecirClimaByFecha,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    public Clima predecirClimaByFecha(LocalDate fecha) {
        if (fecha == null) throw new IllegalArgumentException("Dia es requerido");
        int day = (int) ChronoUnit.DAYS.between(LocalDate.now(), fecha) + 1;

        PlanetasPosiciones ferengiPosicion = ferengi.getPlanetPosition(day);
        PlanetasPosiciones betasoidePosicion = betasoide.getPlanetPosition(day);
        PlanetasPosiciones vulcanoPosicion = vulcano.getPlanetPosition(day);

        Alineados planetasAndSolAlineados = new Alineados(posicionSol, ferengiPosicion);
        if(planetasAndSolAlineados.isInLine(vulcanoPosicion) && planetasAndSolAlineados.isInLine(betasoidePosicion)) {
            logger.debug("Tres planetas y el Sol están alineados, por lo que el día {} es un día de sequía", fecha);
            return Clima.DROUGHT;
        }

        Alineados planetasAlineados = new Alineados(ferengiPosicion, betasoidePosicion);
        if(planetasAlineados.isInLine(vulcanoPosicion)) {
            logger.debug("Tres planetas están alineados pero el Sol no, por lo que el día {} tiene condiciones óptimas", fecha);
            return Clima.OPTIMAL_CONDITIONS;
        }

        Triangulo solDentroTriangulo = new Triangulo(ferengiPosicion, betasoidePosicion, vulcanoPosicion);
        if(solDentroTriangulo.isInside(posicionSol)) {
            logger.debug("Tres planetas en triángulo con el Sol dentro, por lo que el día {} es un día lluvioso", fecha);
            return Clima.RAIN;
        }

        logger.debug("Se aplicó cualquier regla, por lo que no sabemos cómo estará el tiempo el día {}", fecha);
        return Clima.UNKNONW;
    }

    public double getIntensidadLluvia(LocalDate fecha) {
        if (fecha == null) throw new IllegalArgumentException("Dia es requerido");

        int day = (int) ChronoUnit.DAYS.between(LocalDate.now(), fecha) + 1;

        PlanetasPosiciones ferengiPosition = ferengi.getPlanetPosition(day);
        PlanetasPosiciones betasoidePosition = betasoide.getPlanetPosition(day);
        PlanetasPosiciones vulcanoPosition = vulcano.getPlanetPosition(day);

        Alineados line = new Alineados(ferengiPosition, betasoidePosition);
        if(line.isInLine(vulcanoPosition))
            return 0;

        Triangulo planetsTriangle = new Triangulo(ferengiPosition, betasoidePosition, vulcanoPosition);
        return planetsTriangle.getPerimeter();
    }

}
