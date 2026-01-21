package com.SCM.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String meetingtitle;
	private Date meetingdateandtime;
	private String location;
	private String status;

	private LocalDate createddate;
	private LocalTime createdtime;

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

	public LocalDate getCreateddate() {
		return createddate;
	}

	public void setCreateddate(LocalDate createddate) {
		this.createddate = createddate;
	}

	public LocalTime getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(LocalTime createdtime) {
		this.createdtime = createdtime;
	}

}
