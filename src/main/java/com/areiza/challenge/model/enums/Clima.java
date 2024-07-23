package com.areiza.challenge.model.enums;

public enum Clima {
    DROUGHT("Sequía"),
    RAIN("Lluvia"),
    OPTIMAL_CONDITIONS("Presión y temperatura óptimas"),
    UNKNONW("Desconocido");

    private final String value;

    private Clima(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String getValue() {
        return value;
    }
}
