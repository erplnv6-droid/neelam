package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.SCM.model.Group1;
import com.SCM.model.Group2;
import com.SCM.model.Group3;
import com.SCM.model.Group4;
import com.SCM.model.Group5;
import com.SCM.model.Group6;
import com.SCM.model.Group7;
import com.SCM.model.Group8;

import lombok.Data;

@Data
public class Group9Dto {

	private long id;

	private String title;

	private Group1 group1;
	
	private Group2 group2;

	private Group3 group3;

	private Group4 group4;
	
	private Group5 group5;
	private Group6 group6;
	private Group7 group7;
	
	private Group8 group8;
	

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
