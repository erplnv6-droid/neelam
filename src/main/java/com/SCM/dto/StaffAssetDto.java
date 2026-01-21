package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.SCM.model.StaffAssetItem;

import lombok.Data;

@Data
public class StaffAssetDto {

	
	private long id;
	private long staffid;
	private LocalDate date;
	private List<StaffAssetItem> assetItems; 
	
	private LocalDate createddate;
	private LocalTime createdtime;
	private long createdby;
	private String createdbyname;
	private String role;
	
	
	private long updatedby;
	private String updatedbyname;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private String updatedrole;
	
}
