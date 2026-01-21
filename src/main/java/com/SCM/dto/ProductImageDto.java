package com.SCM.dto;

import com.SCM.model.Product;

public class ProductImageDto {
	private int id;
	private String name;
	private String location;
	private String blob;
	private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getBlob() {
		return blob;
	}

	public void setBlob(String blob) {
		this.blob = blob;
	}

	@Override
	public String toString() {
		return "ProductImageDto [id=" + id + ", name=" + name + ", location=" + location + ", product=" + product + "]";
	}

	public ProductImageDto(int id, String name, String location, Product product) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.product = product;
	}

	public ProductImageDto() {

	}

}
