package com.SCM.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.SCM.model.Customer;

import lombok.Data;

@Data
public class CustomerSubContactsDto {

	private int id;
	private String contactname;
	
	@Column(unique = true)
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email is not valid", regexp = "^[a-z-0-9_!#$%&’*+/=?`{|}~^.-]+@[a-z-0-9.-]+$")	
	private String email;
	
	@NotNull
	@Pattern(regexp = "^\\d{10}$", message = "Phone number must be between 10 and 11 characters")
	@Column(unique = true)
	private String phoneno;
	
	private Customer customer;


}
