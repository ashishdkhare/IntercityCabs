package com.bookmycab.controllers;

import com.bookmycab.entities.Cab;
import com.bookmycab.entities.CabHistory;
import com.bookmycab.exceptions.CabOnboardingFailedException;
import com.bookmycab.requests.CabOnboardingRequest;
import com.bookmycab.requests.UpdateCabLocationRequest;
import com.bookmycab.requests.UpdateCabStateRequest;
import com.bookmycab.services.CabService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path={"/cab"})
public class CabController {

    @Autowired
    CabService cabService;

    @RequestMapping(value = "/onboard", method = RequestMethod.POST)
    public ResponseEntity onboard(@RequestBody CabOnboardingRequest cabOnboardingRequest){
        try {
            Cab newcab = cabService.onboardCab(cabOnboardingRequest);
            return new ResponseEntity("Onboarded new cab with id : " + newcab.getCabNumber(), HttpStatus.OK);
        } catch (CabOnboardingFailedException e) {
            return new ResponseEntity("Cab onboarding failed. " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity("Cab onboarding failed. " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/book/{location}", method = RequestMethod.GET)
    public ResponseEntity bookcab(@PathVariable(value="location") @NonNull String location){
        try {
            Cab cab = cabService.bookCab(location.toUpperCase());
            return new ResponseEntity("Cab booking done. Cab number : " + cab.getCabNumber(), HttpStatus.OK);
        } catch( Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/endtrip", method = RequestMethod.POST)
    public ResponseEntity endtrip(@RequestBody UpdateCabLocationRequest updateCabLocationRequest){
        try {
            cabService.endTrip(updateCabLocationRequest);
            return new ResponseEntity("Trip completed for Cab number : " + updateCabLocationRequest.getCabNumber(), HttpStatus.OK);
        } catch( Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Driver is gonna call this function every time he is going the trip
    @RequestMapping(value = "/updatelocation", method = RequestMethod.POST)
    public ResponseEntity  updatelocation(@RequestBody UpdateCabLocationRequest updateCabLocationRequest){
        try {
            cabService.updateCabLocation(updateCabLocationRequest);
            return new ResponseEntity("Updated cab location for cabNumber : " + updateCabLocationRequest.getCabNumber(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/updatestate", method = RequestMethod.POST)
    public ResponseEntity  updatestate(@RequestBody UpdateCabStateRequest updateCabStateRequest){
        try {
            cabService.updateCabState(updateCabStateRequest);
            return new ResponseEntity("Updated cabState for cabNumber : " + updateCabStateRequest.getCabNumber(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/getcabhistory/{cabNumber}", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<CabHistory> getCabHistory(@PathVariable(value="cabNumber") String cabNumber) {
        return new ArrayList<CabHistory>(cabService.getCabHistory(cabNumber));
    }

    /*

    // test api : for debugging only. Uncomment the block to use it.
    @RequestMapping(value = "/getallcabs", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<Cab> getAllCabs() {
        return new ArrayList<Cab>(cabService.getAllCabs());
    }

     */
}
