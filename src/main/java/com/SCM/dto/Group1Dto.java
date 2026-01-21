package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class Group1Dto {

	private long id;
	
	private String title;
	
	private long createdby;
	private String createbyname;
	private String role;
	private LocalDate createddate;
	private LocalTime createdtime;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
}
