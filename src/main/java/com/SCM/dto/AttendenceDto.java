package com.SCM.dto;


import java.time.LocalDate;
import com.SCM.model.Staff;

import lombok.Data;



import java.sql.Date;
import java.time.LocalTime;
import com.SCM.model.Staff;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class AttendenceDto {

	private int id;
//	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss.SSS")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private java.sql.Date dayin;
	private LocalTime dayintime;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
//	@JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss.SSS")
	private java.sql.Date dayout;
	private LocalTime dayouttime;

	private String checkinLocationLatitude;
	private String checkinLocationLongitude;
	private String locationin;
	private String locationout;
	private String checkoutLocationLatitude;
	private String checkoutLocationLongitude;
	private Staff staff;
	private int sid;

	private String status;
	

}
