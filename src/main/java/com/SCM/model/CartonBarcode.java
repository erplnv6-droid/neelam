package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cartonbarcode")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartonBarcode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="productid")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="brandid")
	private Brand brand;
	
	private String eancode;
	private long pcs;
	
	@ManyToOne 
	@JoinColumn(name="galaid")
	private GalaPrefix galaPrefix;
	
	
	
//	private long galaid;
	private String galaname;
	private long galanumber;
	private String galaseriesname;
	
//	private String eancodeimagename;
//	private String eancodeimagepath;
	
//	private String pcsimagename;
//	private String pcsimagepath;
	
//	private String galaseriesimagename;
//	private String galaseriesimagepath;
	
	
	private long stdqty;
	private long emptymasterctnweight;
	private long minweight;
	private long maxweight;
	private long netweight;
	private long grossweight;
	private String operatorname;
	private long length;
	private long width;
	private long height;

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
		
	@ManyToOne
	@JoinColumn(name="mastercid")
	private MasterCartoon masterCartoon;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="cartonbarcodeid")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CartonBarcodeItems> cartonBarcodeItems;

	private long loosepacking;


}
