package com.SCM.model;

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
@Table(name="cartonbarcodeitems")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartonBarcodeItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "product_id")
	private Product product;
	
	private String uom;
	private float qty;
	
	private float rate;
	private float amount;
	
	@ManyToOne
	@JoinColumn(name="bomid")
	private BillOfMaterial billOfMaterialid;


	

//	@ManyToOne
//	private CartonBarcode cartonBarcode;

	
}
