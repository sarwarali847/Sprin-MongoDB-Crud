package com.gpch.mongo.model;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Document(collection = "reservations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation{
	
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	
    @Id
    private Long id;
    
    @NotBlank(message="Please provide a first name.")
    private String firstName;
    
    @NotBlank(message="Please provide a last name.")
    private String lastName;
    
    @Size(max = 10)
    @NotBlank(message="Please enter 10 digit number.")
    private String mobileNumber;
    
    @NotBlank(message="Please provide a email id.")
    private String emailId;
    
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    @NotNull(message="Please provide a date whit the format dd-MM-yyyy HH:mm")
    private LocalDateTime date = LocalDateTime.now();
    

   
	
	

	
	}
    
    
    
    
    
    

