package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name="assetreqform")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssetRequestForm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long staffid;
	private String staffname;
	private LocalDate date;
	private String remarks;
	private String status;
	private LocalDate formdate;
	
	@ManyToOne
	@JoinColumn(name = "staff_id")
	private Staff staff;
	
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
	
	
}
