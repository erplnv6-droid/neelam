package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transporters {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	private String transporterGstin;
	
	private String transporterName;

	public Transporters(long id, String transporterGstin, String transporterName) {
		super();
		this.id = id;
		this.transporterGstin = transporterGstin;
		this.transporterName = transporterName;
	}

	public Transporters() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTransporterGstin() {
		return transporterGstin;
	}

	public void setTransporterGstin(String transporterGstin) {
		this.transporterGstin = transporterGstin;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}
	
	

}

