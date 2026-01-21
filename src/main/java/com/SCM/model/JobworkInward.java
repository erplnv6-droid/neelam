package com.SCM.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class JobworkInward {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String jobsheetno;
	private Date jobsheetdate;
	private String jobtype;
	private String remarks;
	private float grandtotal;
	
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
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;
	
	@ManyToOne
    @JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "jobsheet_id")
	private List<JobworkInwardItems> jobsheetItems;
}
