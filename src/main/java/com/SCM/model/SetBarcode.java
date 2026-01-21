package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
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
@Table(name="setbarcode")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetBarcode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="productid")
	private Product product;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="brandid")
	private Brand brand;
	
	private String productname1;
	private String productname2;
	private String eancode;
	private String quantity;
	private String qrname;
	private String qrimgpath;

	private String qrqtyname;
	private String qrqtyimgpath;
	
	private LocalDate createddate;
	private LocalTime createdtime;
	private long createdby;
	private String createbyname;
	private String role;

	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
}
