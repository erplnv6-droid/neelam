package com.SCM.model;

import java.sql.Date;
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
@Table(name = "productbarcode")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductBarcode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "productid")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "brandid")
	private Brand brand;

	private String productname;
	private String size;
	private String qty;
	private String mrp;
	private String capacity;
	private String diameter;
	private String barcode;
	private String barcodename;
	private String productname1;
	private String productname2;
	private String ourprice;
	private String brandname;
	private String eancode;
	private Date packedOn;
	private long printingqty;
	private long actualqty;
	private String previewlabel;

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
