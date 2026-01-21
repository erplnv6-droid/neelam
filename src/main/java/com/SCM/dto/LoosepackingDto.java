package com.SCM.dto;

import com.SCM.model.Brand;
import com.SCM.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoosepackingDto {

	private Long id;
	private String remarks;
	private Brand brand;	
	private String brandname;
	private  long qty;
	private long pcs;
	private String productname;
	private Product product;
	
}
