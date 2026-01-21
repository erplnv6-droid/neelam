package com.SCM.IndexDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexOpeningStock {
	private int id;
	private int qty;
	private String productName;
	private String eanCode;
}
