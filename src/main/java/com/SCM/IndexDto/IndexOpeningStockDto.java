package com.SCM.IndexDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexOpeningStockDto {

	private int id;
	private int qty;
	private String productname;
	private String eancode;
}
