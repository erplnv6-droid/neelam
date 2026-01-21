package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributorSalesReturnItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	private float mrp;
	private float qty;
	private float total;
	private float gstvalue;
	private float amount;
	private float discount;
	private String text;
	private String igst;
	private float cgstLedger;
	private float sgstLedger;
	private float igstLedger;
	private String unitofmeasurement;
	private int measurement;
	private float dlp;
	private float discount1;
	private float grossamount;
	private String productName;
	private int estimatedDays;
	public String productType;
	private String standardQtyPerBox;
	private String uomSecondary;

	private float staffCancelQty;
	private float retailerCancelQty;
	private float disributorCancelQty;
	
	
	
}
