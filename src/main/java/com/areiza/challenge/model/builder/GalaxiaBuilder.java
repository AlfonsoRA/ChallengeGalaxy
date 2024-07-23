package com.areiza.challenge.model.builder;

import com.areiza.challenge.model.enums.ModoGiro;
import com.areiza.challenge.model.Galaxia;
import com.areiza.challenge.model.Planeta;
public class GalaxiaBuilder {

    public static Galaxia buildGalaxia() {

        Planeta ferengi = new PlanetaBuilder().with(builder -> {
            builder.nombre = "Ferengi";
            builder.kmToTheSun = 500;
            builder.gradosDiarios = 1;
            builder.mododoGiro = ModoGiro.CLOCKWISE;
        }).crearPlaneta();

        Planeta betasoide = new PlanetaBuilder().with(builder -> {
            builder.nombre = "Betasoide";
            builder.kmToTheSun = 2000;
            builder.gradosDiarios = 3;
            builder.mododoGiro = ModoGiro.CLOCKWISE;
        }).crearPlaneta();


        Planeta vulcano = new PlanetaBuilder().with(builder -> {
            builder.nombre = "Vulcano";
            builder.kmToTheSun = 1000;
            builder.gradosDiarios = 5;
            builder.mododoGiro = ModoGiro.COUNTER_CLOCKWISE;
        }).crearPlaneta();

        return new Galaxia(ferengi, betasoide, vulcano);
    }

}
