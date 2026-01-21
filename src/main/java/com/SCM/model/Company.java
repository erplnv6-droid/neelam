package com.SCM.model;

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
public class Company {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String cname;
	private String phone;
	private String email;
	private String gst;
	private String address;
	private String zipcode;
	private String cities;
    	
	@ManyToOne
	@JoinColumn(name = "countryId")
	private Country country;
    
	@ManyToOne
	@JoinColumn(name = "statesId")
	private States states;
}
