package com.bookmycab.requests;

import com.bookmycab.enums.CabState;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CabOnboardingRequest {
    @NonNull
    String cabNumber;
    @NonNull
    String location;

    public String getLocation(){
        return location.toUpperCase();
    }

    public String getCabNumber() {
        return cabNumber;
    }
}
