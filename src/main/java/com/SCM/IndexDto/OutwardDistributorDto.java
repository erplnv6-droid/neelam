package com.SCM.IndexDto;

public class OutwardDistributorDto {
	
	private long id;
	
	private String outwardDate;
	
	private String distributor;
	
	private String product;
	
	private long quantity;
	
	private double amount;
	
	private double rate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOutwardDate() {
		return outwardDate;
	}

	public void setOutwardDate(String outwardDate) {
		this.outwardDate = outwardDate;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public OutwardDistributorDto(long id, String outwardDate, String distributor, String product, long quantity,
			double amount, double rate) {
		super();
		this.id = id;
		this.outwardDate = outwardDate;
		this.distributor = distributor;
		this.product = product;
		this.quantity = quantity;
		this.amount = amount;
		this.rate = rate;
	}

	public OutwardDistributorDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
