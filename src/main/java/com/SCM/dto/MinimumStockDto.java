package com.SCM.dto;

import java.time.LocalDate;

import com.SCM.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class MinimumStockDto {

	private int id;
	private int wid;
	private int stockqty;
	private LocalDate stockdate;
	private Product product;
}
