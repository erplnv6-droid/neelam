package com.SCM.model;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="staffasset")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffAsset {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long staffid;

	private LocalDate date;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "staffassetid")
	private List<StaffAssetItem> assetItems; 
	
	private LocalDate createddate;
	private LocalTime createdtime;
	private long createdby;
	private String createdbyname;
	private String role;
	
	
	
	private long updatedby;
	private String updatedbyname;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private String updatedrole;
	
	
	
}
