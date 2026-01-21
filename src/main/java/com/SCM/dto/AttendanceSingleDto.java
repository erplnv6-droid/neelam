package com.SCM.dto;

import java.sql.Date;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class AttendanceSingleDto {
	private Date date;
	private Date dayin;
	private Date dayout;
	private int staff;
	private LocalTime dayintime;
	private LocalTime dayouttime;

	private LocalTime duration;


	private String status;

	
}
