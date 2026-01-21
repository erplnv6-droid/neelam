package com.SCM.model;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "refreshtoken")
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne
	@JoinColumn(name = "staff_id", referencedColumnName = "id")
	private Staff user;

	@OneToOne
	@JoinColumn(name = "distributor_id", referencedColumnName = "id")
	private Distributor distributor;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "retailer_id", referencedColumnName = "id")
	private Retailer retailer;

	@Column(nullable = false, unique = true)
	private String token;

	@Column(nullable = false)
	private Instant expiryDate;

	public RefreshToken() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Staff getUser() {
		return user;
	}

	public void setUser(Staff user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Instant getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
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

	public void setUser(Distributor distributor) {
		this.distributor = distributor;
	}

	public void setUser(Retailer retailer) {
		this.retailer = retailer;
	}

	@Override
	public String toString() {
		return "RefreshToken [id=" + id + ", user=" + user + ", distributor=" + distributor + ", retailer=" + retailer
				+ ", token=" + token + ", expiryDate=" + expiryDate + "]";
	}

}
