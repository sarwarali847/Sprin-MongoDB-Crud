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
import com.gpch.mongo.controller.ReservationController;
import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.repository.ReservationRepository;
import com.gpch.mongo.service.ReservationService;
import com.gpch.mongo.service.InvalidRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;


@WebMvcTest(ReservationController.class)
public class MongoApplicationTest {
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;


	@MockBean
	ReservationService service;

	@MockBean
	ReservationRepository repo;
	
	

	Reservation RECORD_1 = new Reservation((long)1, "sarwar", "shaikh","9898989898", "sarwar123@gmail.com", LocalDateTime.now());
	Reservation RECORD_2 = new Reservation((long)2, "sahil", "khan","9898989898" , "sahil123@gmail.com", LocalDateTime.now());
	Reservation RECORD_3 = new Reservation((long)3, "hasan", "shaikh", "9898989898", "hasan123@gmail.com", LocalDateTime.now());


	@Test
	void contextLoads() {
	}
	
	@Test
	public void getAllRecords_success() throws Exception {
		List<Reservation> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

		Mockito.when(service.getAllReservations()).thenReturn(records);
		// When findAll called then ready with records (No DB calls)
		mockMvc.perform(MockMvcRequestBuilders
				.get("/reservations")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()) //200
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[1].firstName", is("sahil")));
	}
	
	@Test
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

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/SaveReservation")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(record));

		mockMvc.perform(mockRequest)
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.firstName", is("John")));
	}
	
	@Test
	public void updateReservationRecord_success() throws Exception {
		Reservation record = Reservation.builder()
				.id((long)1)
				.firstName("SARWAR")
				.lastName("Shaikh")
		        .mobileNumber("8675678908")
				.emailId("shaikh@gmail.com")
				.date(LocalDateTime.now())
				.build();

		Mockito.when(service.findReservationById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));

		Mockito.when(service.updateReservation(record)).thenReturn(record);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/update/reservation/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(record));

		mockMvc.perform(mockRequest)
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.lastName", is("Shaikh")));
	}
	

	@Test
	public void deleteReservationById_success() throws Exception {

		Mockito.when(service.findReservationById(RECORD_2.getId())).thenReturn(Optional.of(RECORD_2));

		mockMvc.perform(MockMvcRequestBuilders.delete("/DeleteReservationById/2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void deleteReservationById_notFound() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/DeleteReservationById/8").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidRequestException))
				.andExpect(result -> assertEquals("User with ID 8 does not exist.",
						result.getResolvedException().getMessage()));
	}
	
	
	@Test
	public void searchReservation_success() throws Exception {
		List<Reservation> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
		Mockito.when(service.searchCustomer(RECORD_1.getFirstName())).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/searchReservation/sarwar").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$[0].lastName", is("shaikh")));
	}
	
	

	@Test
	void findReservationById() {
		Assert.notNull(service.findReservationById((long)1));
	}
	
	@Test
	void findAllReservations() {
		Assert.notNull(service.getAllReservations());
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
	        assertThat(allCustomer.size()).isGreaterThanOrEqualTo(0);
	    }
	 
	 
	

	
	
	

}
