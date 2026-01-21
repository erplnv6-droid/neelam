package com.SCM.dto;

import java.sql.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class SchedulerDto {

	private int id;
	private String meetingtitle;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date meetingdateandtime;
	
	private String location;
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMeetingtitle() {
		return meetingtitle;
	}

	public void setMeetingtitle(String meetingtitle) {
		this.meetingtitle = meetingtitle;
	}

	public Date getMeetingdateandtime() {
		return meetingdateandtime;
	}

	public void setMeetingdateandtime(Date meetingdateandtime) {
		this.meetingdateandtime = meetingdateandtime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
