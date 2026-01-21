package com.SCM.dto;


import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.Map;

import com.SCM.model.Branch;

import lombok.Data;

@Data
public class WarehouseDto {

	private int id;
	private String name;
	private String location;
	private String type;
	private Branch branch;
	private List<Integer> wareid;
	private Map<Integer, String> warehousename;


	private long createdby;
	private String createbyname;
	private String role;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;

}
