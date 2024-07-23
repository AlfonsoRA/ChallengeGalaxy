package com.areiza.challenge.model;

import com.areiza.challenge.model.enums.ModoGiro;

public class MovimientoPlanetas {
    private Integer gradosDiarios;
    private ModoGiro modoGiro;

    public MovimientoPlanetas(Integer gradosDiarios, ModoGiro modoGiro) {
        if (gradosDiarios == null) throw new IllegalArgumentException("Se requieren grados de diario");
        if (modoGiro == null) throw new IllegalArgumentException("Se requiere modo de giro");

        this.gradosDiarios = gradosDiarios;
        this.modoGiro = modoGiro;
    }

    public Integer getGradosDiarios() {
        return gradosDiarios;
    }

    public ModoGiro getModoGiro() {
        return modoGiro;
    }

}
