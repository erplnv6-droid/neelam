package com.SCM.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class StaffLiveLocationDto {
	
	private Long staffid;
	private Double lattitude;
	private Double longitude;
}
