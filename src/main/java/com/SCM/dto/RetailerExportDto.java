package com.SCM.dto;

import com.SCM.model.Zone;

public class RetailerExportDto {

	private int aseid;
	private int asmid;
	private String staffname;
	private int rsmid;
	private int nsmid;
	private int stateid;
	private int zonesid;
	private Zone zone;

	public int getAseid() {
		return aseid;
	}

	public void setAseid(int aseid) {
		this.aseid = aseid;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public int getAsmid() {
		return asmid;
	}

	public void setAsmid(int asmid) {
		this.asmid = asmid;
	}

	public int getRsmid() {
		return rsmid;
	}

	public void setRsmid(int rsmid) {
		this.rsmid = rsmid;
	}

	public int getNsmid() {
		return nsmid;
	}

	public void setNsmid(int nsmid) {
		this.nsmid = nsmid;
	}

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}

	public int getZonesid() {
		return zonesid;
	}

	public void setZonesid(int zonesid) {
		this.zonesid = zonesid;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

}
