package com.SCM.dto;

import com.SCM.model.Distributor;
import com.SCM.model.Retailer;

import lombok.Data;

@Data
public class LocationDto {

	private int id;
	private String checkinLocationDate;
	private String checkoutLocationDate;
	private String checkinLocation;
	private String checkoutLocation;
	private String checkinLocationLatitude;
	private String checkinLocationLongitude;
	private String checkoutLocationLatitude;
	private String checkoutLocationLongitude;
	private int staffId;
	private Retailer retailer;
	private Distributor distributor;

}
