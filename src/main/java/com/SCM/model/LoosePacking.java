package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
 
@Entity
@Data
public class LoosePacking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String remarks;
	private  float qty;
	private long pcs;
	
	private String operatorname;
	private LocalDate createddate;
	private LocalTime createdtime;
	
	@ManyToOne
	@JoinColumn(name="bid")
	private Brand brand;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
}
