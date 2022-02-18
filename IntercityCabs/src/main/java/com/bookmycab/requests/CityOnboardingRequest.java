package com.bookmycab.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CityOnboardingRequest {
    @NonNull
    String city;

    @NonNull
    Double latitude;

    @NonNull
    Double longitude;

    public String getCity(){
        return city.toUpperCase();
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
