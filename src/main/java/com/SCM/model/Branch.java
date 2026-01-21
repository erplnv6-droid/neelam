package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String branchname;
	private String phoneno;
	private String email;
	private String gstno;
	private String address;
	private String zipcode;
	private String bankname;
	private long accountno;
	private String branch;
	private String ifsc;
	private String cities;
	
	private LocalDate createddate;
	private LocalTime createdtime;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long createdby;
	private String createbyname;
	private String role;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;


	@ManyToOne
	@JoinColumn(name = "countryId")
	private Country country;

	@ManyToOne
	@JoinColumn(name = "statesId")
	private States states;

	

	
	
	

}
