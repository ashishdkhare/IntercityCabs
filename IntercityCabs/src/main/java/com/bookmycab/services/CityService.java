package com.bookmycab.services;

import com.bookmycab.entities.City;
import com.bookmycab.exceptions.CityOnboardingFailedException;
import com.bookmycab.repositories.CityRepository;
import com.bookmycab.requests.CityOnboardingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;

    public void onboard(CityOnboardingRequest cityOnboardingRequest) throws CityOnboardingFailedException {
        City city = new City();
        try {
            city.setCity(cityOnboardingRequest.getCity());
            city.setLatitude(cityOnboardingRequest.getLatitude());
            city.setLongitude(cityOnboardingRequest.getLongitude());
            if(!cityRepository.existsById(city.getCity())){
                cityRepository.save(city);
            }
            else {
                throw new CityOnboardingFailedException(cityOnboardingRequest.getCity() + " already exists in database");
            }
        } catch (NullPointerException npe){
            throw new CityOnboardingFailedException(npe.getMessage());
        }
    }

    // test
    public List<City> getAll(){
        List<City> list = new LinkedList<>();
        cityRepository.findAll().forEach(list::add);
        return list;
    }
}
