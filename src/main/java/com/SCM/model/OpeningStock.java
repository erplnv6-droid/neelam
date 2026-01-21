package com.SCM.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Cascade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class OpeningStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int pid;
	private int wid;
	private int qty;
	private String date;


	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	@JsonIgnoreProperties(value = { "openingStock" }, allowSetters = true)
	private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	

	@Override
	public String toString() {
		return "OpeningStock{" + "id=" + id + ", pid=" + pid + ", wid=" + wid + ", qty=" + qty + ", date='" + date
				+ '\'' + '}';
	}
}
