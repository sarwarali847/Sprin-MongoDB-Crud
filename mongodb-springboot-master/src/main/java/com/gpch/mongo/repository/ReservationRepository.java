package com.gpch.mongo.repository;


import com.gpch.mongo.model.Reservation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, Long> {
}
