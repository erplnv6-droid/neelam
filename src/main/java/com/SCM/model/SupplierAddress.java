package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class SupplierAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String supplier_delivery_address;
	private String state;
	private long pincode;
}
