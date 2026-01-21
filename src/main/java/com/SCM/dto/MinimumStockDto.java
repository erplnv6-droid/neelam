package com.SCM.dto;

import java.time.LocalDate;

import com.SCM.model.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinimumStockDto {

	private int id;
	private int wid;
	private int stockqty;
	private LocalDate stockdate;
	private Product product;
}
