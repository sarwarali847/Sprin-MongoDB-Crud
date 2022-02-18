package com.gpch.mongo.service;

import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

	@Autowired
    private ReservationRepository reservationRepository;
	
	@Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation saveReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    public Iterable<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public void deleteAllReservations(){
        reservationRepository.deleteAll();
    }

    public void deleteReservationById(Long id){
        reservationRepository.deleteById(id);
    }

    public Optional<Reservation> findReservationById(Long id){
        return reservationRepository.findById(id);
    }

	public Iterable<Reservation> searchCustomer(String search) {
		if(!(search==null))
		{
			return mongoTemplate.find(Query.query(new Criteria()
                    .orOperator(Criteria.where("firstName").regex(search, "i"),
                                Criteria.where("lastName").regex(search, "i"),
                                Criteria.where("emailId").regex(search, "i"),
                                Criteria.where("mobileNumber").regex(search, "i"))
                    ), Reservation.class);
			}
			

		return reservationRepository.findAll();
		
	}
}
