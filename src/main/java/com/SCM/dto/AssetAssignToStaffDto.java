package com.SCM.dto;

import java.time.LocalDate;

import com.SCM.model.Asset;

import lombok.Data;

@Data
public class AssetAssignToStaffDto {

	private long id;
	private long staffid;
	private long createdbyid;
	private String createdbyname;
	private LocalDate createddate;
	private String remarks;
	private Asset asset;
	private LocalDate formdate;
	
}
