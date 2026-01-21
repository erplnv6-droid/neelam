package com.SCM.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String number;
	private String cname;
	private String gstno;
	private String email;
	private String address;
	private String openbal;
	private String opendate;
	private String pan;
	private String zipcode;
	private String cities;

	@ManyToOne
	@JoinColumn(name = "countryId")
	private Country country;

	@ManyToOne
	@JoinColumn(name = "statesId")
	private States states;

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "customer_id")
	private List<CustomerSubContacts> customerSubContacts;
	
}
