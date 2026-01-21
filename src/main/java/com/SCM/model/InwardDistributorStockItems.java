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
public class InwardDistributorStockItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int quantity;
	private int altqty;
	private String per;
	private float grossamount;
	private float discount1;
	private int measurement;
	private float rate;
	private float mrp;
	private float discount;
	private float discountamount;
	private float discountamount1;
	private float total;
	private float igst;
	private float cgst;
	private float sgst;
	private float gst;
	private float amount;
	private float uomPrimary;
	private float uomSecondary;
	private float tradediscount;
	private float schemeDiscount;
	private String productName;
	private String productType;
	private String unitofmeasurement;
	private float quantity_placed_kg;
	private String calunitofmeasurement;
	private float quantity_placed;
	private float dlp;
	private String standardQtyPerBox;
	private float netAmount;
	private float gstvalue;


	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
  
}
