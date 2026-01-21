package com.SCM.dto;


import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;

import com.SCM.model.OpeningStock;
import com.SCM.model.ProductImage;

import lombok.Data;

@Data
public class ProductDto {
	
	private int id;
	private String productName;
	private String shortName;
	private String eanCode;
	private String standardQtyPerBox;
	private String category;
	private String uomPrimary;
	private String uomSecondary;
	private String mrp;
	private String capacity;
	private String diameter;
	private String hsnCode;
	private String brand;
	private String igst;
	private String cgst;
	private String sgst;
	private int igstLedger;
	private int cgstLedger;
	private int sgstLedger;
	private String productType;
	private String productGroup;
	private float dlp;
//	private Product product;
	private int pid;
	private int wid;
	private int qty;
	private String date;
	private ProductImage image;
	private OpeningStock openingStock;
	private ProductImage productImage;
	private int productImgId;
	private List<ProductImage> productImages;
	private String productStringImage;
	private String productKind;

	private long createdby;
	private String createbyname;

	private String role;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	private double costprice;
	private String uom;

}
