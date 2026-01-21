package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class ContractManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate fromdate;
	private LocalDate todate;
	private String remarks;
	private float taxableamount;
	private float taxamount;
	private float totalamount;
	private String docname;
	private String doclocation;
	private String contracttype;
	
	
	private LocalDate createddate;
	private LocalTime createdtime;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	
	private long createdby;
	private String createbyname;
	private String role;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;

	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	


}
