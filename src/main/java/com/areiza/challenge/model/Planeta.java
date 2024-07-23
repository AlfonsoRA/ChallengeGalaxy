package com.areiza.challenge.model;

import com.areiza.challenge.model.enums.ModoGiro;

public class Planeta {
    private static Integer ORBIT_DEGREES = 360;

    private String nombre;
    private Integer kmToTheSun;
    private MovimientoPlanetas movimientoPlanetas;

    public Planeta(String nombre, Integer kmToTheSun, MovimientoPlanetas movimientoPlanetas) {
        if (kmToTheSun == null) throw new IllegalArgumentException("Se requiere distancia al sol.");
        if (movimientoPlanetas == null) throw new IllegalArgumentException("Se requiere movimiento.");

        this.nombre = nombre;
        this.kmToTheSun = kmToTheSun;
        this.movimientoPlanetas = movimientoPlanetas;
    }

    public Integer getGradosFromX(Integer dia) {
        if (dia == null) throw new IllegalArgumentException("El día no debe ser null.");

        Integer gradosActuales = (movimientoPlanetas.getGradosDiarios() * dia) % ORBIT_DEGREES;

        if (ModoGiro.CLOCKWISE.equals(movimientoPlanetas.getModoGiro()) && gradosActuales != 0) {
            gradosActuales = ORBIT_DEGREES - gradosActuales;
        }

        return gradosActuales;
    }

    public PlanetasPosiciones getPlanetPosition(Integer dia) {
        if (dia == null) throw new IllegalArgumentException("El día no debe ser null.");

        Integer grados = getGradosFromX(dia);
        double radians = Math.toRadians(grados);

        double x = this.kmToTheSun * Math.cos(radians);
        double y = this.kmToTheSun * Math.sin(radians);

        return new PlanetasPosiciones(x,y);
    }

    public String getNombre() {
        return nombre;
    }

}
