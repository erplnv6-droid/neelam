package com.SCM.dto;


import java.time.LocalTime;

import java.util.List;

import com.SCM.projection.AttendanceReportProjection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttendanceReportDto {
	private Long id;
	private String staffName;
	private List<AttendanceSingleDto> attendanceData;

	private int totalPresent;
	private int totalAbsent;
	private int totalHoliday;
	private int totalHolidayPresent;
	private int totalWorkOff;
	private int totalWorkOffPresent;
	private int totalLeave;
	private int totalHalfDays;
	private String totalWorkingHours;
	

}
