package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobworkInwardItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String productType;
	private float jobsheet_qty;
	private float jobsheet_qty_kg;
	private float amount;
	private float rate;
	private float dlp;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
}
