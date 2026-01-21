package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String unit;
	private float qtykg;
	private int qty;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
}
