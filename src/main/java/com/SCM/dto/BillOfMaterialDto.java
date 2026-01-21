package com.SCM.dto;

import java.util.List;

import com.SCM.model.BillOfMaterialItems;
import com.SCM.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class BillOfMaterialDto {
	
	
	private int id;
	private String bomNo;
	private float rate;
	private float amount;
	private String description;
	private Product product;
	private List<BillOfMaterialItemsDto> bomItem;
	private List<BillOfMaterialItems> bomItems;
	
	
	@Override
	public String toString() {
		return "BillOfMaterialDto [id=" + id + ", bomNo=" + bomNo + ", rate=" + rate + ", amount=" + amount
				+ ", description=" + description + ", product=" + product + ", bomItems=" + bomItems + "]";
	}
	
	
	
	
}
