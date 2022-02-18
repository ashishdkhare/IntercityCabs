package com.bookmycab.services;

import com.bookmycab.entities.Cab;
import com.bookmycab.entities.CabHistory;
import com.bookmycab.enums.CabState;
import com.bookmycab.exceptions.CabNotAvailableException;
import com.bookmycab.exceptions.CabOnboardingFailedException;
import com.bookmycab.exceptions.UpdateCabLocationException;
import com.bookmycab.exceptions.UpdateCabStateException;
import com.bookmycab.repositories.CabHistoryRepository;
import com.bookmycab.repositories.CabRepository;
import com.bookmycab.repositories.CityRepository;
import com.bookmycab.requests.CabOnboardingRequest;
import com.bookmycab.requests.UpdateCabLocationRequest;
import com.bookmycab.requests.UpdateCabStateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class CabService {

    private static final String unknown = "En-route";

    @Autowired
    CabRepository cabRepository;

    @Autowired
    CabHistoryRepository cabHistoryRepository;

    @Autowired
    CityRepository cityRepository;

    // onboard cab only on those cities where we have service else throw an error
    public Cab onboardCab(CabOnboardingRequest cabOnboardingRequest) throws CabOnboardingFailedException {
        try {
            Cab cab = new Cab();
            if(cabOnboardingRequest.getCabNumber() == null){
                throw new CabOnboardingFailedException("cabnumber is missing in onboarding request.");
            }
            if(!StringUtils.hasText(cabOnboardingRequest.getLocation())){
                throw new CabOnboardingFailedException("location is missing in onboarding request.");
            }
            cab.setCabstate(CabState.IDLE);
            cab.setCabNumber(cabOnboardingRequest.getCabNumber());
            cab.setLocation(cabOnboardingRequest.getLocation());
            long millis=System.currentTimeMillis();
            cab.setLastupdated(new Date(millis));
            Optional<Cab> oldEntry = cabRepository.findById(cabOnboardingRequest.getCabNumber());
            if(oldEntry.isPresent()){
                throw new CabOnboardingFailedException("Cab is already onboarded for location : " + oldEntry.get().getLocation());
            }
            if(cityRepository.existsById(cabOnboardingRequest.getLocation())){
                Cab newcab = cabRepository.save(cab);
                cabHistoryRepository.save(new CabHistory(new Date(millis), newcab.getCabNumber(),
                        newcab.getCabstate(), newcab.getLocation()));
                return newcab;
            } else {
                throw new CabOnboardingFailedException("Cab service not available in " + cabOnboardingRequest.getLocation());
            }
        } catch (Exception e){
            throw new CabOnboardingFailedException(e.getMessage());
        }
    }

    // Set current state on_trip and put entry in history table too
    public Cab bookCab(String location) throws CabNotAvailableException, UpdateCabStateException, UpdateCabLocationException {
        if(!cityRepository.existsById(location)){
            throw new CabNotAvailableException("This location is currently not being served.");
        }
        List<Cab> cabs = cabRepository.findByLocation(location);
        Cab cab = cabs.stream().filter( c -> c.getCabstate().equals(CabState.IDLE)).
                sorted(Comparator.comparing(Cab::getLastupdated)).findFirst().orElse(null);
        if(cab == null){
            throw new CabNotAvailableException("No cabs are available nearby at the moment.");
        } else {
            updateCabLocation(new UpdateCabLocationRequest(cab.getCabNumber(), unknown));
            updateCabState(new UpdateCabStateRequest(cab.getCabNumber(), CabState.ON_TRIP, unknown));
        }
        return cab;
    }

    // Set current state idle and put entry in history table too
    public void endTrip(UpdateCabLocationRequest updateCabLocationRequest) throws CabNotAvailableException, UpdateCabStateException, UpdateCabLocationException {
        if(StringUtils.hasText(updateCabLocationRequest.getLocation()) &&
                StringUtils.hasText(updateCabLocationRequest.getCabNumber())){
            updateCabState(new UpdateCabStateRequest(updateCabLocationRequest.getCabNumber(), CabState.IDLE,
                                                     updateCabLocationRequest.getLocation()));}
    }

    public void updateCabLocation(UpdateCabLocationRequest updateCabLocationRequest) throws UpdateCabLocationException {
        if(StringUtils.hasText(updateCabLocationRequest.getLocation()) &&
                StringUtils.hasText(updateCabLocationRequest.getCabNumber())){
            Cab cab = cabRepository.findById(updateCabLocationRequest.getCabNumber()).orElse(null);
            if(cab == null){
                throw new UpdateCabLocationException("Invalid request. cabNumber is invalid");
            }
            if(cab.getCabstate().equals(CabState.ON_TRIP)){
                throw new UpdateCabLocationException("Cab should be stopped first before trying to update location");
            }
            cab.setLocation(updateCabLocationRequest.getLocation());
            cabRepository.save(cab);
        } else {
            throw new UpdateCabLocationException("Invalid request. either cabNumber or location is either missing");
        }
    }

    public void updateCabState(UpdateCabStateRequest updateCabStateRequest) throws UpdateCabStateException {
        try {
            Cab cab = cabRepository.findById(updateCabStateRequest.getCabNumber()).get();
            if(!cab.getCabstate().equals(updateCabStateRequest.getCabstate())){
                cab.setCabstate(updateCabStateRequest.getCabstate());
                cab.setLocation(updateCabStateRequest.getLocation());
                cabRepository.save(cab);
                // save it in history too
                long millis=System.currentTimeMillis();
                cabHistoryRepository.save(new CabHistory(new Date(millis), updateCabStateRequest.getCabNumber(),
                        updateCabStateRequest.getCabstate(), updateCabStateRequest.getLocation()));
            }
        } catch(Exception e) {
            throw new UpdateCabStateException("Invalid request. cabNumber or cabState is either missing or invalid");
        }
    }

    public List<CabHistory> getCabHistory(String cabNumber){
        List<CabHistory> list = new LinkedList<>();
        cabHistoryRepository.findByCabNumber(cabNumber.toUpperCase()).forEach(list::add);
        return list;
    }

    /*
    // test api helper function: for debugging only. Uncomment the block to use it.
    public List<Cab> getAllCabs(){
        List<Cab> list = new LinkedList<>();
        cabRepository.findAll().forEach(list::add);
        return list;
    }
     */
}
