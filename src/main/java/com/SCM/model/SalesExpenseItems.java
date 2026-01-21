package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SalesExpenseItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String travelfrom;
	private String travelto;
	private String modeoftravel;
	private float travelfare;
	private String otherexp;
	private float otherexpamount;
	private double total;
	private String remarks;
	private String approvalbyrsm;
	private String approvalbyadmin;
	private float approvedexpensebyrsm;
	private float approvedexpensebyadmin;
private String adminstatus;
private String rsmstatus;


}
