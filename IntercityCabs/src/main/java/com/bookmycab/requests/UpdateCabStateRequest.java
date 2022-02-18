package com.bookmycab.requests;

import com.bookmycab.enums.CabState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class UpdateCabStateRequest {
    @NonNull
    String cabNumber;

    @NonNull
    CabState cabstate;

    @NonNull
    String location;
}
