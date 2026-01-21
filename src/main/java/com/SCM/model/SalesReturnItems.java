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
public class SalesReturnItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sritemid;
	private int rate;
	private int mrp;
	private int srquantity;
	private int sraltquantity;
	private String per;
//	private int discount;
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
	private float schemeDiscount;
	private float netAmount;
	private String unitofmeasurement;
	private String calunitofmeasurement;
	private float srquantitykgs;
	private float grossamount;
	private String hsncode;
	
	private int SlNo;
    private String PrdDesc;
    private String IsServc;

    private double Qty;
    private int FreeQty;
    private String Unit;
   
    private double TotAmt;
    private double Discount;
    private double AssAmt;
	private double GstRt;
	private double IgstAmt;
	private double CgstAmt;
	private double SgstAmt;
	private double TotItemVal;


	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;


	


}
