package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String checkinLocationDate;
	private String checkinLocation;
	private String checkinLocationLatitude;
	private String checkinLocationLongitude;
	private String checkoutLocationDate;
	private String checkoutLocation;
	private String checkoutLocationLatitude;
	private String checkoutLocationLongitude;
	private LocalDate createddate;
	private LocalTime createdtime;
	private LocalTime checkinLocationTime;
	private LocalTime checkoutLocationTime;

	public Location() {
	}

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Staff staff;

	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "retailer_id")
	private Retailer retailer;

	@OneToOne
	private Distributor distributor;

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCheckinLocationDate() {
		return checkinLocationDate;
	}

	public void setCheckinLocationDate(String checkinLocationDate) {
		this.checkinLocationDate = checkinLocationDate;
	}

	public String getCheckoutLocationDate() {
		return checkoutLocationDate;
	}

	public void setCheckoutLocationDate(String checkoutLocationDate) {
		this.checkoutLocationDate = checkoutLocationDate;
	}

	public String getCheckinLocation() {
		return checkinLocation;
	}

	public void setCheckinLocation(String checkinLocation) {
		this.checkinLocation = checkinLocation;
	}

	public String getCheckoutLocation() {
		return checkoutLocation;
	}

	public void setCheckoutLocation(String checkoutLocation) {
		this.checkoutLocation = checkoutLocation;
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

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public LocalTime getCheckinLocationTime() {
		return checkinLocationTime;
	}

	public void setCheckinLocationTime(LocalTime checkinLocationTime) {
		this.checkinLocationTime = checkinLocationTime;
	}

	public LocalTime getCheckoutLocationTime() {
		return checkoutLocationTime;
	}

	public void setCheckoutLocationTime(LocalTime checkoutLocationTime) {
		this.checkoutLocationTime = checkoutLocationTime;
	}

}
