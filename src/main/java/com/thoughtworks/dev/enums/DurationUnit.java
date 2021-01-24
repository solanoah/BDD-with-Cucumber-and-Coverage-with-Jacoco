package com.thoughtworks.dev.enums;


public enum DurationUnit {

    MINUTES("min"),
    LIGHTNING("l");

    private final String value;

    DurationUnit(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
