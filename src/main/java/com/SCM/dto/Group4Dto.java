package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.model.Group1;
import com.SCM.model.Group2;
import com.SCM.model.Group3;

import lombok.Data;

@Data
public class Group4Dto {

	private long id;

	private String title;

	private Group1 group1;
	

	private Group2 group2;

	private Group3 group3;
	
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
