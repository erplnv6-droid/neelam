package com.SCM.model;

import java.sql.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "distributor_opening_stock")

public class DistributorOpeningStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Date user_date;

	private Date date;

	private Integer quantity;

	private String uom;
	private double grandtotal;

	@ManyToOne(cascade =  CascadeType.DETACH)
	@JoinColumn(name = "distributor_id")
	private Distributor distributor;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "distributorOpeningstock_id")
	private List<DistributorOpeningStockItems> distributorOpeningStockItems;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date lastUpdate;

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

	public LocalDate getUpdateddate() {
		return this.updateddate;
	}

	public void setUpdateddate(LocalDate updateddate) {
		this.updateddate = updateddate;
	}

	public LocalTime getUpdatedtime() {
		return this.updatedtime;
	}

	public void setUpdatedtime(LocalTime updatedtime) {
		this.updatedtime = updatedtime;
	}

	public long getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(long updatedby) {
		this.updatedby = updatedby;
	}

	public String getUpdatedbyname() {
		return this.updatedbyname;
	}

	public void setUpdatedbyname(String updatedbyname) {
		this.updatedbyname = updatedbyname;
	}

	public String getUpdatedrole() {
		return this.updatedrole;
	}

	public void setUpdatedrole(String updatedrole) {
		this.updatedrole = updatedrole;
	}

	public long getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}

	public String getCreatebyname() {
		return this.createbyname;
	}

	public void setCreatebyname(String createbyname) {
		this.createbyname = createbyname;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDate getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(LocalDate createddate) {
		this.createddate = createddate;
	}

	public LocalTime getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(LocalTime createdtime) {
		this.createdtime = createdtime;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getUser_date() {
		return this.user_date;
	}

	public void setUser_date(Date user_date) {
		this.user_date = user_date;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Distributor getDistributor() {
		return this.distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public java.util.Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(java.util.Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public double getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(double grandtotal) {
		this.grandtotal = grandtotal;
	}

	public List<DistributorOpeningStockItems> getDistributorOpeningStockItems() {
		return this.distributorOpeningStockItems;
	}

	public void setDistributorOpeningStockItems(List<DistributorOpeningStockItems> distributorOpeningStockItems) {
		this.distributorOpeningStockItems = distributorOpeningStockItems;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

}
