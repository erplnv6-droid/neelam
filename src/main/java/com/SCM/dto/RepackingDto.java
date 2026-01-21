package com.SCM.dto;

import java.util.List;

import com.SCM.model.Brand;
import com.SCM.model.RepackingItems;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepackingDto {

	private int id;
	private String remarks;
	private String brandname;
	private Brand brand;
	private List<RepackingItems> repackingItems;
}
