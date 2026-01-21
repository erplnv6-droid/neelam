package com.SCM.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="staffassetitem")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffAssetItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long mrp;
	private long qty;
	private long amount;
	private String remarks;
	
}
