package com.gpch.mongo.controller;

import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.service.ReservationService;
import com.neosoft.mongo.myexception.ResourceNotFoundException;
import com.gpch.mongo.service.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    private ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    private Iterable<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
    
    @GetMapping("/GetReservationById/{id}")
    private Optional<Reservation> GetReservationById(@PathVariable Long id){
		if(reservationService.findReservationById(id).isPresent())
		{
			return reservationService.findReservationById(id);
		}
		else{
			throw new InvalidRequestException("User with ID " + 
        			id + " does not exist.");
		}	
    }
    
    @DeleteMapping("/DeleteReservationById/{id}")
    private void DeleteReservationById(@PathVariable Long id) throws ResourceNotFoundException {
    	if(reservationService.findReservationById(id).isPresent())
		{
			reservationService.deleteReservationById(id);
		}
		else{
			throw new InvalidRequestException("User with ID " + 
        			id + " does not exist.");
		}	
    }
    
    
    @PostMapping("/SaveReservation")
    private Reservation addReservation(@RequestBody Reservation reservation) {
    	return reservationService.saveReservation(reservation);
    }
    
    @PutMapping("/update/reservation/{id}")
	public Reservation updateInfo(@RequestBody Reservation reservation, @PathVariable Long id) {
		if(reservationService.findReservationById(id).isPresent()) {
			reservation.setId(id);
			return reservationService.updateReservation(reservation);
		}
		else {
			throw new InvalidRequestException("User with ID " + 
        			reservation.getId() + " does not exist.");
		}	
	}
    
    @GetMapping("/searchReservation/{search}")
    private Iterable<Reservation> searchReservation(@PathVariable String search){
    	return reservationService.searchCustomer(search);
    }
   
    
    
    

}
