package com.SCM.model;

import java.sql.Date;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;




import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(uniqueConstraints = {
@UniqueConstraint(columnNames = {"sid","dayin"})})
public class Attendence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date dayin;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date dayout;
	
	private LocalTime dayintime;
	
	private LocalTime dayouttime;
	
	private String checkinLocationLatitude;
	private String checkinLocationLongitude;
	private String checkoutLocationLatitude;
	private String checkoutLocationLongitude;



	
	private String status;

	@ManyToOne
	@JoinColumn(name = "staffuserid")
	private Staff staff;
	
	private int sid;

	
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDayin() {
		return dayin;
	}

	public void setDayin(Date dayin) {
		this.dayin = dayin;
	}

	public LocalTime getDayintime() {
		return dayintime;
	}

	public void setDayintime(LocalTime dayintime) {
		this.dayintime = dayintime;
	}

	public LocalTime getDayouttime() {
		return dayouttime;
	}

	public void setDayouttime(LocalTime dayouttime) {
		this.dayouttime = dayouttime;
	}

	public Date getDayout() {
		return dayout;
	}

	public void setDayout(Date dayout) {
		this.dayout = dayout;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getCheckinLocationLatitude() {
		return checkinLocationLatitude;
	}

	public void setCheckinLocationLatitude(String checkinLocationLatitude) {
		this.checkinLocationLatitude = checkinLocationLatitude;
	}

	public String getCheckinLocationLongitude() {
		return checkinLocationLongitude;
	}

	public void setCheckinLocationLongitude(String checkinLocationLongitude) {
		this.checkinLocationLongitude = checkinLocationLongitude;
	}

	public String getCheckoutLocationLatitude() {
		return checkoutLocationLatitude;
	}

	public void setCheckoutLocationLatitude(String checkoutLocationLatitude) {
		this.checkoutLocationLatitude = checkoutLocationLatitude;
	}

	public String getCheckoutLocationLongitude() {
		return checkoutLocationLongitude;
	}

	public void setCheckoutLocationLongitude(String checkoutLocationLongitude) {
		this.checkoutLocationLongitude = checkoutLocationLongitude;
	}

}



