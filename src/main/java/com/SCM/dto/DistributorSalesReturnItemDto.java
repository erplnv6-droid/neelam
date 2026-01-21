package com.SCM.dto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.model.Product;

import lombok.Data;

@Data
public class DistributorSalesReturnItemDto {
	
	private long id;
	
	private Product product;

	private float qty;
	private float total;
	private float gstvalue;
	private float amount;
	private float discount;

	private String unitofmeasurement;
	private int measurement;
	private float dlp;
	private float discount1;
	private float grossamount;
	private String productName;


}
