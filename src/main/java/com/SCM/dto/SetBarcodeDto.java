package com.SCM.dto;

import com.SCM.model.Brand;
import com.SCM.model.Product;

import lombok.Data;

@Data
public class SetBarcodeDto {

	private long id;
	
	private Product product;
	private Brand brand;
	private String productname1;
	private String productname2;
	private String eancode;
	private String quantity;
	
	
	private String qrname;
	private String qrimgpath;
	
	private String qrqtyname;
	private String qrqtyimgpath;
}
