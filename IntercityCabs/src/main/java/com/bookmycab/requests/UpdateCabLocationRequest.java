package com.bookmycab.requests;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class UpdateCabLocationRequest {
    @NonNull
    String cabNumber;

    @NonNull
    String location;

    public String getCabNumber() {
        return cabNumber;
    }

    public String getLocation() {
        return location.toUpperCase();
    }
}
