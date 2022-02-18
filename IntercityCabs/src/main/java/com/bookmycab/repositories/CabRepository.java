package com.bookmycab.repositories;

import com.bookmycab.entities.Cab;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CabRepository extends CrudRepository<Cab, String> {
    List<Cab> findByLocation(String location);
}
