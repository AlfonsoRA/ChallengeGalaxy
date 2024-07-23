package com.areiza.challenge.model.builder;

import com.areiza.challenge.model.MovimientoPlanetas;
import com.areiza.challenge.model.enums.ModoGiro;
import com.areiza.challenge.model.Planeta;

import java.util.function.Consumer;

public class PlanetaBuilder {

    public String nombre;
    public Integer kmToTheSun;
    public Integer gradosDiarios;
    public ModoGiro mododoGiro;


    public PlanetaBuilder with(Consumer<PlanetaBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }


    public Planeta crearPlaneta() {
        return new Planeta(nombre, kmToTheSun, new MovimientoPlanetas(gradosDiarios, mododoGiro));
    }

}

