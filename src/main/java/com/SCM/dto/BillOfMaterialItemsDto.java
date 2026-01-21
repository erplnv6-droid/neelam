package com.SCM.dto;

import com.SCM.model.Product;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillOfMaterialItemsDto {
	
	private int id;
	private Product product;
	private String uom;
	private float qty;
	private String kg;
	private String uom2;
	private float rate;
	private float amount;
}
