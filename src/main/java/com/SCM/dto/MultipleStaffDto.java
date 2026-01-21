package com.SCM.dto;

import java.util.List;

import lombok.Data;

@Data
public class MultipleStaffDto {

	private List<Integer> rsmid;
	private List<Integer> asmid;
	private List<Integer> aseid;
	private List<Integer> nsmid;
}
