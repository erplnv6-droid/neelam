package com.SCM.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class DistributorToStaffDto {

	private List<Integer> rsmid;
	private List<Integer> asmid;
	private List<Integer> aseid;
	private List<Integer> nsmid;
}
