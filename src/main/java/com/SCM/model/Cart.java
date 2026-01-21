package com.SCM.model;


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

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name="distributor_id")
	private Distributor distributor;
	
	
	@ManyToOne
	@JoinColumn(name="retailer_id")
	private Retailer retailer;
	
	
	@ManyToOne
	@JoinColumn(name="staff_id")
	private Staff staff;
	
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="cart_id")
	private List<CartItems> cartItems;
	
	public Cart() {
		
	}

	

	public Cart(String name, Distributor distributor, Retailer retailer, Staff staff, List<CartItems> cartItems) {
		super();
		this.name = name;
		this.distributor = distributor;
		this.retailer = retailer;
		this.staff = staff;
		this.cartItems = cartItems;
	}



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

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public Staff getStaff() {
		return staff;
	}

	public List<CartItems> getCartItems() {
		return cartItems;
	}



	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}



	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", name=" + name + ", distributor=" + distributor + ", retailer=" + retailer
				+ ", staff=" + staff + ", cartItems=" + cartItems + "]";
	}
	
	

}
