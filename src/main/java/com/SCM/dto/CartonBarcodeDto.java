package com.SCM.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.SCM.model.Brand;
import com.SCM.model.CartonBarcodeItems;
import com.SCM.model.GalaPrefix;
import com.SCM.model.MasterCartoon;
import com.SCM.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartonBarcodeDto {

	
private long id;
	

	private Product product;
	private Brand brand;

	
	private String eancode;
	private long pcs;
	

	private GalaPrefix galaPrefix;
	private String galaname;
	private String galaseriesname;
	private long galanumber;

	
//	private String eancodeimagename;
//	private String eancodeimagepath;
//	
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
	
	
	@ManyToOne
	@JoinColumn(name="mastercid")
	private MasterCartoon masterCartoon;

	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="cartonbarcodeid")
	private List<CartonBarcodeItems> cartonBarcodeItems;

	private LocalDate createddate;

	private long loosepacking;



}
