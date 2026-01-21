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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class BillOfMaterial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String bomNo;
	private float rate;
	private float amount;
	private String description;
	private LocalDate createddate;
	private LocalTime createdtime;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long createdby;
	private String createbyname;
	private String role;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "bill_of_material_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<BillOfMaterialItems> bomItems;

	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "product_id")
	private Product product;

	@Override
	public String toString() {
		return "BillOfMaterial [id=" + id + ", bomNo=" + bomNo + ", rate=" + rate + ", amount=" + amount
				+ ", description=" + description + ", bomItems=" + bomItems + ", product=" + product + "]";
	}
	
}
