package com.gpch.mongo;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpch.mongo.controller.ReservationThymeleafController;
import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.repository.ReservationRepository;
import com.gpch.mongo.service.ReservationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class MongoApplicationTest {


	@Autowired
	ReservationService service;

	@Autowired
	ReservationRepository repo;
	
	

	Reservation RECORD_1 = new Reservation((long)1, "sarwar", "shaikh","9898989898", "sarwar123@gmail.com", LocalDateTime.now());
	Reservation RECORD_2 = new Reservation((long)2, "sahil", "khan","9898989898" , "sahil123@gmail.com", LocalDateTime.now());
	Reservation RECORD_3 = new Reservation((long)3, "hasan", "shaikh", "9898989898", "hasan123@gmail.com", LocalDateTime.now());


	
	/*@Test
	public void getAllRecords_success() throws Exception {
		List<Reservation> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

		Mockito.when(service.getAllReservations()).thenReturn(records);
		// When findAll called then ready with records (No DB calls)
		mockMvc.perform(MockMvcRequestBuilders
				.get("/reservations-ui")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()) //200
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[1].firstName", is("sahil")));
	}*/
	
	/*@Test
	public void createRecord_success() throws Exception {
		Reservation record = Reservation.builder()
				.id((long)4)
				.firstName("John")
				.lastName("Doe")
		        .mobileNumber("9878978978")
				.emailId("john@gmail.com")
				.date(LocalDateTime.now())
				.build();

		Mockito.when(service.saveReservation(record)).thenReturn(record);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/save-reservation")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(record));

		mockMvc.perform(mockRequest)
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.firstName", is("John")));
	}*/
	
	

	@Test
	void findReservationById() {
		Assert.notNull(service.findReservationById((long)1));
	}
	
	@Test
	void findAllReservations() {
		Assert.notNull(service.getAllReservations());
	}
	
	@Test
	void addReservation() {
		
		Reservation reservation = new Reservation((long)1,"sarwar","shaikh","9898989898","sarwar@gmail.com",LocalDateTime.now());
		Assert.notNull(service.saveReservation(reservation));
	}
	
	
	@Test
	void updateReservation() {
	
		Reservation reservation = new Reservation((long)1,"sarwar","ali","9878987678","ss@gmail.com",LocalDateTime.now());
		Assert.notNull(service.saveReservation(reservation));
	}
	
	
	@Test
	void deleteReservationById() {
		service.deleteReservationById((long)1);
	}
	
	@Test
	void search() {
		Assert.notNull(service.searchCustomer("sarwar"));
	}

	
	 @Test
	    void findAll_success() {
	        List<Reservation> allCustomer = (List<Reservation>) service.getAllReservations();
	        assertThat(allCustomer.size()).isGreaterThanOrEqualTo(1);
	    }
	 
	 
	

	
	
	

}
