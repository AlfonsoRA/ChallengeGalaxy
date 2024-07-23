package com.areiza.challenge.model.geometric;

import com.areiza.challenge.model.PlanetasPosiciones;

import java.util.Optional;

public class Alineados {
    private static double PRECISION = 0.01;

    private PlanetasPosiciones puntoA;
    private PlanetasPosiciones puntoB;


    public Alineados(PlanetasPosiciones puntoA, PlanetasPosiciones puntoB) {
        if (puntoA == null && puntoB == null) throw new IllegalArgumentException("Los puntos no deben ser null");

        this.puntoA = puntoA;
        this.puntoB = puntoB;
    }


    //Calculando la pendiente m = (yB-yA)/(xB-xA)
    private Optional<Double> getPendiente() {
        double deltaX = this.puntoB.getX() - this.puntoA.getX();

        if (deltaX == 0d) {
            return Optional.empty(); // es una linea vertical
        }

        double deltaY = this.puntoB.getY() - this.puntoA.getY();
        return Optional.of(deltaY / deltaX);
    }


    // Calculando b = y - mx
    private Double getYAxisIntersection(Double slope) {
        return this.puntoA.getY() - slope * this.puntoA.getX();
    }

    public boolean isInLine(PlanetasPosiciones punto) {
        if (punto == null) throw new IllegalArgumentException("punto no debe ser null");

        return this.getPendiente().map(m -> {
            // Un punto está en línea si es cierto que y = mx + b
            double calculandoY = m * punto.getX() + this.getYAxisIntersection(m);
            return Math.abs(calculandoY - punto.getY()) < PRECISION;
        }).orElse(
                // Es una línea vertical, por lo que para estar en línea los puntos deben tener la misma x.
                Math.abs(this.puntoA.getX() - punto.getX()) < PRECISION
        );
    }


    public double getDistancia() {
        double yPowDistance = Math.pow(this.puntoB.getY() - this.puntoA.getY(), 2);
        double xPowDistance = Math.pow(this.puntoB.getX() - this.puntoA.getX(), 2);
        return Math.sqrt(yPowDistance + xPowDistance);
    }

}
