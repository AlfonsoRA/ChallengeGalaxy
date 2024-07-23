package com.areiza.challenge.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PlanetasPosiciones {

    private double x;
    private double y;

    public PlanetasPosiciones(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(x=" + x + ", y=" + y + ")";
    }
}
