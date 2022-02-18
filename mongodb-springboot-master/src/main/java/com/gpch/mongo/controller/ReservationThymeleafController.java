package com.gpch.mongo.controller;

import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.repository.ReservationRepository;
import com.gpch.mongo.service.ReservationService;
import com.neosoft.mongo.myexception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;

@Controller
public class ReservationThymeleafController {
	
	private static final Logger log = LoggerFactory.getLogger(ReservationThymeleafController.class);

	@Autowired
    private ReservationService reservationService;
	private ReservationRepository reservationRepo;
	
	@Autowired
	private com.gpch.mongo.service.SequenceGeneratorService sequenceGeneratedService;

    @Autowired
    public ReservationThymeleafController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations-ui")
    public String reservations(Model model) {
    	log.info("GET : /reservation --> called");
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservations";
    }

    @GetMapping("/delete-reservation/{id}")
    public String removeReservation(@PathVariable("id") Long id, Model model) {
    	log.info("DELETE : /reservation/{ "+id+" } --> called");
        reservationService.deleteReservationById(id);
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservations";
    }
    
    @GetMapping("/edit-add-reservation/{id}")
	public String editCarForm(@PathVariable Long id, Model model) {
		
		model.addAttribute("reservation",reservationService.findReservationById(id));
		
		return "edit-reservation";
	}

    
    @PostMapping("/reservation-edit/{id}")
	public String updateCar(@PathVariable Long id, 
			@ModelAttribute("reservation") Reservation reservation2,Model model) throws ResourceNotFoundException {
		
		
		
    	log.info("PUT : /reservation/{ "+id+" } --> called");
		Reservation reservation = reservationService.findReservationById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :"+id));
		reservation2.setId(id);
		reservationService.saveReservation(reservation2);
		return "redirect:/reservations-ui";
		
	}
    
    
    
    

    @PostMapping("/save-reservation")
    public String editReservation(@ModelAttribute("reservation") @Valid @RequestBody Reservation reservation,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-edit";
        }
        reservation.setId(sequenceGeneratedService.generateSequence(Reservation.SEQUENCE_NAME));
        log.info("POST : /reservation/["+reservation.getId()+"] --> called");
        reservationService.saveReservation(reservation);
        return "redirect:reservations-ui";
    }
    
    @GetMapping("/reservation/new")
	public String createCarsForm(Model model) {
		Reservation res = new Reservation();
		model.addAttribute("reservation", res);
		
		return "add-edit";
	}
    
    
    @GetMapping("/searchcustomer")
    public String search(Model model, @Param("search") String search) {
    	log.info("SEARCH : /reservation/[ '"+search+"' ] --> called");
        model.addAttribute("listCustomer", reservationService.searchCustomer(search));
        model.addAttribute("search", search);
        return "search";
    }
    
}
