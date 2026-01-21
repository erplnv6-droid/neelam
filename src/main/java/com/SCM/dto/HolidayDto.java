package com.SCM.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class HolidayDto {
private int id;
	
	private Date date;
	
	private String occasion;
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
