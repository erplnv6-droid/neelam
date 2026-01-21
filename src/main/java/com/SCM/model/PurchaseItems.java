package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int puitemid;
	private int rate;
	private int mrp;
	private int puquantity;
	private int pualtquantity;
	private String per;
	private int discount;
	private float total;
	private float gst;
	private float igst;
	private float cgst;
	private float sgst;
	private float amount;
	private float gstvalue;
	private float dlp;
	private String productName;
	private String productType;
	private String standardQtyPerBox;
	private float uomPrimary;
	private float uomSecondary;
	private float tradeDiscount;
	private String unitofmeasurement;
	private String calunitofmeasurement;
	private float puquantitykgs;
	private float grossamount;
	private String hsncode;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

}
