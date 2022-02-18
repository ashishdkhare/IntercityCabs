package com.bookmycab.enums;

import lombok.ToString;

@ToString
public enum CabState {
    IDLE("IDLE"),
    ON_TRIP("ON_TRIP");

    private final String state;

    CabState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

