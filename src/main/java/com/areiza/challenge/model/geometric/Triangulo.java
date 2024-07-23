package com.areiza.challenge.model.geometric;

import com.areiza.challenge.model.PlanetasPosiciones;

public class Triangulo {
    private PlanetasPosiciones verticeA;
    private PlanetasPosiciones verticeB;
    private PlanetasPosiciones verticeC;

    public Triangulo(PlanetasPosiciones puntoA, PlanetasPosiciones puntoB, PlanetasPosiciones puntoC) {
        if (puntoA == null && puntoB == null && puntoC == null) throw new IllegalArgumentException("puntos cannot be null");

        this.verticeA = puntoA;
        this.verticeB = puntoB;
        this.verticeC = puntoC;
    }


    public double getPerimeter() {
        double distanceAB = new Alineados(this.verticeA, this.verticeB).getDistancia();
        double distanceBC = new Alineados(this.verticeB, this.verticeC).getDistancia();
        double distanceCA = new Alineados(this.verticeC, this.verticeA).getDistancia();

        return distanceAB + distanceBC + distanceCA;
    }


    // Haciendo 3 nuevos triángulos usando el punto dado, si la suma de sus 3 áreas es igual al área del triángulo original, el punto está dentro.
    public boolean isInside(PlanetasPosiciones punto) {
        Double areaOriginal = area(this.verticeA, this.verticeB, this.verticeC);

        double areaReplacingA = area(punto, this.verticeB, this.verticeC);
        double areaReplacingB = area(this.verticeA, punto, this.verticeC);
        double areaReplacingC = area(this.verticeA, this.verticeB, punto);

        return areaOriginal.equals(areaReplacingA + areaReplacingB + areaReplacingC);
    }


    // Dados tres puntos, A, B y C, el área del triángulo es el módulo de [ Ax (By − Cy) + Bx (Cy − Ay) + Cx (Ay − By) ] / 2
    private double area(PlanetasPosiciones puntoA, PlanetasPosiciones puntoB, PlanetasPosiciones puntoC) {
        double tmp = puntoA.getX() * (puntoB.getY() - puntoC.getY()) +
                puntoB.getX() * (puntoC.getY() - puntoA.getY()) +
                puntoC.getX() * (puntoA.getY() - puntoB.getY());
        return Math.abs(tmp / 2);
    }

}
