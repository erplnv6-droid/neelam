package com.SCM.dto;



import java.time.LocalDate;
import java.time.LocalTime;

import com.SCM.model.Group1;
import com.SCM.model.Group2;

import lombok.Data;

@Data
public class Group3Dto {

	private long id;

	private String title;

	private Group1 group1;
	
	private Group2 group2;
	

	private LocalDate createddate;
	private LocalTime createdtime;
	private long createdby;
	private String createbyname;
	private String role;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	
}
