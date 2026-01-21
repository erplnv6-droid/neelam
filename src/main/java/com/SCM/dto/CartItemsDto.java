package com.SCM.dto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemsDto {
	
	private int id;
	private String unit;
	private float qtykg;
	private int qty;
	
	
//    private String brand;
//
//    private String capacity;
//
//    private String category;
//    
//    private String product_name;
//    
//    private String short_name;
//    
//    private String product_type;
//    
//    private String uom_primary;
//
//    private String uom_secondary;
//
//    private String location; // image path
//    
//    private String ean_code;
//    
//    private String hsn_code;
//
//    private String diameter;
//
//    private double dlp; // Display List Price
//
//    private double mrp;
//
//    private double standard_qty_per_box;
//
//    private String cgst;
//
//    private String sgst;
//
//    private String igst;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	
	
	
	
}