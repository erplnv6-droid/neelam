package com.SCM.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesOrderItemsResponse {

	private int salesorderitemId;
	private float soqty;
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
	private float soquantity_placed_kg;
	private float soquantity_placed;
	private float dlp;
	private float netAmount;
	private float gstvalue;
	private String productName;
	private String productType;
	private String standardQtyPerBox;
	private String unitofmeasurement;
	private int dcitemspending;
	private int dcitemsplaced;
	private String dcstatus;
	private String tradeName;
	private String buyerorder;
	
	
	public SalesOrderItemsResponse(int salesorderitemId, float soqty, int altsoqty, String per, float grossamount,
			float discount1, int someasurement, float rate, float mrp, float discount, float discountamount,
			float discountamount1, float total, float igst, float cgst, float sgst, float gst, float amount,
			float uomPrimary, float uomSecondary, float tradediscount, float schemeDiscount, float soquantity_placed_kg,
			float soquantity_placed, float dlp, float netAmount, float gstvalue, String productName, String productType,
			String standardQtyPerBox,String unitofmeasurement,int dcitemspending, int dcitemsplaced, String dcstatus, String tradeName,
			String buyerorder) {
		super();
		this.salesorderitemId = salesorderitemId;
		this.soqty = soqty;
		this.altsoqty = altsoqty;
		this.per = per;
		this.grossamount = grossamount;
		this.discount1 = discount1;
		this.someasurement = someasurement;
		this.rate = rate;
		this.mrp = mrp;
		this.discount = discount;
		this.discountamount = discountamount;
		this.discountamount1 = discountamount1;
		this.total = total;
		this.igst = igst;
		this.cgst = cgst;
		this.sgst = sgst;
		this.gst = gst;
		this.amount = amount;
		this.uomPrimary = uomPrimary;
		this.uomSecondary = uomSecondary;
		this.tradediscount = tradediscount;
		this.schemeDiscount = schemeDiscount;
		this.soquantity_placed_kg = soquantity_placed_kg;
		this.soquantity_placed = soquantity_placed;
		this.dlp = dlp;
		this.netAmount = netAmount;
		this.gstvalue = gstvalue;
		this.productName = productName;
		this.productType = productType;
		this.standardQtyPerBox = standardQtyPerBox;
		this.unitofmeasurement = unitofmeasurement;
		this.dcitemspending = dcitemspending;
		this.dcitemsplaced = dcitemsplaced;
		this.dcstatus = dcstatus;
		this.tradeName = tradeName;
		this.buyerorder = buyerorder;
	}
	
}
