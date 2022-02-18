package com.bookmycab.repositories;

import com.bookmycab.entities.CabHistory;
import com.bookmycab.helpers.CabHistoryId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CabHistoryRepository extends CrudRepository<CabHistory, CabHistoryId> {
    List<CabHistory> findByCabNumber(String cabNumber);
}
