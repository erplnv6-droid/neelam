package com.SCM.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttendanceSummaryReportDto {
	private int statusCount;
	private String status;
	public AttendanceSummaryReportDto(int statusCount, String status) {
		super();
		this.statusCount = statusCount;
		this.status = status;
	}
	public AttendanceSummaryReportDto() {
		
	}
	
	
}
