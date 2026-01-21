package com.SCM.model;

import javax.persistence.*;

@Entity
public class State_Zone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String state_name;
	private String statecode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getStatecode() {
		return statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

}
