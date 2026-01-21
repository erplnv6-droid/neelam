package com.SCM.dto;

import java.time.LocalDate;

import com.SCM.model.Brand;
import com.SCM.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MasterCartoonDto {

	private long id;
	
	
	private Product product;

	private Brand brand;
	
	private long qty;
	private long emptymasterqty;
	private long minweight;
	private long maxweight;
	private long length;
	private long width;
	private long height;
	private String eancode;
	private String productname1;
	private String productname2;
	private long stdqty;
	private long pcs;
	private LocalDate createddate;

	private long netweight;
	private long grossweight;
	private String operatorname;
	private boolean status;


}
