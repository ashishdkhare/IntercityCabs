package com.bookmycab.repositories;

import com.bookmycab.entities.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, String> {
}
