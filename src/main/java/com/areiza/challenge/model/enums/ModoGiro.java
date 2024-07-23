package com.areiza.challenge.model.enums;

public enum ModoGiro {

    CLOCKWISE(-1),
    COUNTER_CLOCKWISE(1);

    private final Integer value;

    private ModoGiro(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

}
