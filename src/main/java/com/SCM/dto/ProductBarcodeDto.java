package com.SCM.dto;

import java.sql.Date;

import com.SCM.model.Brand;
import com.SCM.model.Product;

import lombok.Data;

@Data
public class ProductBarcodeDto {

	private long id;

	private Product product;
	
	private Brand brand;
	private String productname;
	private String size;
	private String qty;
	private String mrp;
	private String capacity;
	private String diameter;
	private String barcode;
	private String eancode;

	private String barcodename;
	private String productname1;
	private String productname2;
	private String ourprice;
	private String brandname;
	
	private Date packedOn;
	private long printingqty;
	private long actualqty;
	private String previewlabel;
}
