package com.SCM.dto;

import com.SCM.model.Product;

import lombok.Data;


@Data
public class SalesOrderItemsDto {

	private int salesorderitemId;
	private int soqty;
	private int altsoqty;
	private String per;
	private float grossamount;
	private float discount1;
	private int someasurement;
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
	private float soquantity_placed_kg;
	private float soquantity_placed;
	private float dlp;
	private String standardQtyPerBox;
	private float netAmount;
	private float gstvalue;
	private String dcitemspending;
	private String dcitemsplaced;
	private String dcstatus;
	private Product product;
}
