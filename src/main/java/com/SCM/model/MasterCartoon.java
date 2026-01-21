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
@Table(name = "mastercartoon")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterCartoon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "productid")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "brandid")
	private Brand brand;

	private long qty;
	private long emptymasterqty;
	private long minweight;
	private long maxweight;
	private long length;
	private long width;
	private long height;
	private String eancode;
	private String brandname;

	private String productname1;
	private String productname2;
	private long stdqty;
	private long pcs;

	private long netweight;
	private long grossweight;

	@Column(updatable = false)
	private String operatorname;

	private boolean status;

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
