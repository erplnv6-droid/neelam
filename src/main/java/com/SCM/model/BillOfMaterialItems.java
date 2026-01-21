package com.SCM.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BillOfMaterialItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "product_id")
	private Product product;
	
	private String uom;
	private float qty;
	private String kg;
	private String uom2;
	private float rate;
	private float amount;
	
	
	@Override
	public String toString() {
		return "BillOfMaterialItems [id=" + id + ", product=" + product + ", uom=" + uom + ", qty=" + qty + ", rate="
				+ rate + ", amount=" + amount + "]";
	}
}
