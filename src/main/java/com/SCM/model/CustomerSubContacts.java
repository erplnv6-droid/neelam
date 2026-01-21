package com.SCM.model;


import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.Data;

@Entity
@Data
public class CustomerSubContacts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String contactname;
	private String email;
	private String phoneno;
	
	private LocalDate createddate;
	private LocalTime createdtime;

}
