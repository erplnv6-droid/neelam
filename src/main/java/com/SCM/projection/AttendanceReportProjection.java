package com.SCM.projection;

import java.sql.Date;
import java.time.LocalTime;

import com.SCM.model.Staff;

public interface AttendanceReportProjection {
//	Integer getId();
	Date getDate();

	Date getDayin();

	Date getDayout();

	Integer getStaff();

	LocalTime getDayintime();

	LocalTime getDayouttime();

//	LocalTime getDuration();

	LocalTime getduration();

	String getStatus();

}
