package com.bookmycab.controllers;

import com.bookmycab.entities.City;
import com.bookmycab.exceptions.CityOnboardingFailedException;
import com.bookmycab.requests.CityOnboardingRequest;
import com.bookmycab.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CityOnboardingController {

    @Autowired
    CityService cityService;

    @RequestMapping(value = "/city/onboard", method = RequestMethod.POST)
    public ResponseEntity  onboard(@RequestBody CityOnboardingRequest cityOnboardingRequest){
        City newcity = null;
        try {
            cityService.onboard(cityOnboardingRequest);
            return new ResponseEntity("Onboarded new city : " + cityOnboardingRequest.getCity(), HttpStatus.OK);
        } catch (CityOnboardingFailedException e) {
            return new ResponseEntity("City onboarding failed. " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/getallcity")
    public @ResponseBody List<City> getProducts() {
        return new ArrayList<City>(cityService.getAll());
    }
}
