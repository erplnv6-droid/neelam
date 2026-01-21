package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String productName;
	private String shortName;
	private String eanCode;
	private String standardQtyPerBox;
	private String category;
	private String uom;
	private String uomPrimary;
	private String uomSecondary;
	private String mrp;
	private String capacity;
	private String diameter;
	private String hsnCode;
	private String brand;
	private String igst;
	private String cgst;
	private String sgst;
	private int igstLedger;
	private int cgstLedger;
	private int sgstLedger;
	private double costprice;

	private String productType;
	private String productGroup;
	private float dlp;
	private LocalDate createddate;
	private LocalTime createdtime;

	private String productKind;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.DETACH }, mappedBy = "product", fetch = FetchType.EAGER)
	private List<OpeningStock> openingStock;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.DETACH }, mappedBy = "product")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ProductImage> productImages;


	public String getProductKind() {
		return productKind;
	}

	public void setProductKind(String productKind) {
		this.productKind = productKind;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public float getDlp() {
		return dlp;
	}

	public void setDlp(float dlp) {
		this.dlp = dlp;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEanCode() {
		return eanCode;
	}

	public void setEanCode(String eanCode) {
		this.eanCode = eanCode;
	}

	public String getStandardQtyPerBox() {
		return standardQtyPerBox;
	}

	public void setStandardQtyPerBox(String standardQtyPerBox) {
		this.standardQtyPerBox = standardQtyPerBox;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUomPrimary() {
		return uomPrimary;
	}

	public void setUomPrimary(String uomPrimary) {
		this.uomPrimary = uomPrimary;
	}

	public String getUomSecondary() {
		return uomSecondary;
	}

	public void setUomSecondary(String uomSecondary) {
		this.uomSecondary = uomSecondary;
	}

	public String getMrp() {
		return mrp;
	}

	public void setMrp(String mrp) {
		this.mrp = mrp;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getIgst() {
		return igst;
	}

	public void setIgst(String igst) {
		this.igst = igst;
	}

	public String getCgst() {
		return cgst;
	}

	public void setCgst(String cgst) {
		this.cgst = cgst;
	}

	public String getSgst() {
		return sgst;
	}

	public void setSgst(String sgst) {
		this.sgst = sgst;
	}

	public int getIgstLedger() {
		return igstLedger;
	}

	public void setIgstLedger(int igstLedger) {
		this.igstLedger = igstLedger;
	}

	public int getCgstLedger() {
		return cgstLedger;
	}

	public void setCgstLedger(int cgstLedger) {
		this.cgstLedger = cgstLedger;
	}

	public int getSgstLedger() {
		return sgstLedger;
	}

	public void setSgstLedger(int sgstLedger) {
		this.sgstLedger = sgstLedger;
	}

	@JsonIgnore
	public List<OpeningStock> getOpeningStock() {
		return openingStock;
	}

	public void setOpeningStock(List<OpeningStock> openingStock) {
		this.openingStock = openingStock;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public LocalDate getCreateddate() {
		return createddate;
	}

	public void setCreateddate(LocalDate createddate) {
		this.createddate = createddate;
	}

	public LocalTime getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(LocalTime createdtime) {
		this.createdtime = createdtime;
	}

	public double getCostprice() {
		return costprice;
	}

	public void setCostprice(double costprice) {
		this.costprice = costprice;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}
	
}
