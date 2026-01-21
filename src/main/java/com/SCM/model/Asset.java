package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="asset")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String assetsname;
	private long mrp;
	private String srno;
	private String remarks;
	
	@Column(updatable = false)
	private LocalDate dateofpurchase;
	
	@Column(updatable = false)
	private LocalDate expiryDate;
	
	private String status;
	private String invoiceno;
	
	private LocalDate invoicedate;
	private String insuranceprovidername;
	private String insurancepolicyno;
	private LocalDate ipnstartdate;
	private LocalDate ipnenddate;
	
	private String insurancefilename;
	private String insurancefilelocation;
	
	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	@Column(updatable = false)
	private LocalDate createddate;
	@Column(updatable = false)
	private LocalTime createdtime;
	@Column(updatable = false)
	private long createdby;
	@Column(updatable = false)
	private String createdbyname;
	@Column(updatable = false)
	private String role;
	
	private long updatedby;
	private String updatedbyname;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private String updatedrole;
	
	
	
}
