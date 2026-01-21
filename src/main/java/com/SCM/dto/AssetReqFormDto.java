package com.SCM.dto;

import java.time.LocalDate;

import com.SCM.model.Staff;

import lombok.Data;

@Data

public class AssetReqFormDto {
	private long id;
	private long staffid;
	private String staffname;
	private LocalDate date;
	private String remarks;
	private String status;
	private String role;
	private LocalDate formdate;
	private Staff staff;
}
