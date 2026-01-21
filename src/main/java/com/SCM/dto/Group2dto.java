package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.model.Group1;

import lombok.Data;

@Data
public class Group2dto {

	private long id;

	private String title;
	
	private Group1 group1;
	
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
