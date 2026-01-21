package com.SCM.dtoReports;

public class DistributorMonthWiseReport {
	private String month;
	
	private String year;

	private double order_qty_kg;
	
	private double order_qty_kg_pcs;
	
	private double rate;
	
	private double order_value;
	
	private double order_qty_pcs_set;
	
	private double rate_set;
	
	private double order_value_set;
	
	private double dispatch_qty_kgs;
	
	private double dispatch_qty_kgs_pcs;
	
	private double rate_dispatch;
	
	private double dispatch_value;
	
	private double dispatch_qty_pcs_set;
	
	private double rate_dispatch_set;
	
	private double dispatch_value_set;
	
	private double achievmnet_qty_kgs;
	
	private double achievmnet_qty_kgs_pcs;
	
	private double achievment_kg_value;
	
	private double achievment_qty_pcs_set;
	
	private double achievment_set_value;

	public DistributorMonthWiseReport(String month, String year, double order_qty_kg, double order_qty_kg_pcs,
			double rate, double order_value, double order_qty_pcs_set, double rate_set, double order_value_set,
			double dispatch_qty_kgs, double dispatch_qty_kgs_pcs, double rate_dispatch, double dispatch_value,
			double dispatch_qty_pcs_set, double rate_dispatch_set, double dispatch_value_set, double achievmnet_qty_kgs,
			double achievmnet_qty_kgs_pcs, double achievment_kg_value, double achievment_qty_pcs_set,
			double achievment_set_value) {
		super();
		this.month = month;
		this.year = year;
		this.order_qty_kg = order_qty_kg;
		this.order_qty_kg_pcs = order_qty_kg_pcs;
		this.rate = rate;
		this.order_value = order_value;
		this.order_qty_pcs_set = order_qty_pcs_set;
		this.rate_set = rate_set;
		this.order_value_set = order_value_set;
		this.dispatch_qty_kgs = dispatch_qty_kgs;
		this.dispatch_qty_kgs_pcs = dispatch_qty_kgs_pcs;
		this.rate_dispatch = rate_dispatch;
		this.dispatch_value = dispatch_value;
		this.dispatch_qty_pcs_set = dispatch_qty_pcs_set;
		this.rate_dispatch_set = rate_dispatch_set;
		this.dispatch_value_set = dispatch_value_set;
		this.achievmnet_qty_kgs = achievmnet_qty_kgs;
		this.achievmnet_qty_kgs_pcs = achievmnet_qty_kgs_pcs;
		this.achievment_kg_value = achievment_kg_value;
		this.achievment_qty_pcs_set = achievment_qty_pcs_set;
		this.achievment_set_value = achievment_set_value;
	}

	public DistributorMonthWiseReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public double getOrder_qty_kg() {
		return order_qty_kg;
	}

	public void setOrder_qty_kg(double order_qty_kg) {
		this.order_qty_kg = order_qty_kg;
	}

	public double getOrder_qty_kg_pcs() {
		return order_qty_kg_pcs;
	}

	public void setOrder_qty_kg_pcs(double order_qty_kg_pcs) {
		this.order_qty_kg_pcs = order_qty_kg_pcs;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getOrder_value() {
		return order_value;
	}

	public void setOrder_value(double order_value) {
		this.order_value = order_value;
	}

	public double getOrder_qty_pcs_set() {
		return order_qty_pcs_set;
	}

	public void setOrder_qty_pcs_set(double order_qty_pcs_set) {
		this.order_qty_pcs_set = order_qty_pcs_set;
	}

	public double getRate_set() {
		return rate_set;
	}

	public void setRate_set(double rate_set) {
		this.rate_set = rate_set;
	}

	public double getOrder_value_set() {
		return order_value_set;
	}

	public void setOrder_value_set(double order_value_set) {
		this.order_value_set = order_value_set;
	}

	public double getDispatch_qty_kgs() {
		return dispatch_qty_kgs;
	}

	public void setDispatch_qty_kgs(double dispatch_qty_kgs) {
		this.dispatch_qty_kgs = dispatch_qty_kgs;
	}

	public double getDispatch_qty_kgs_pcs() {
		return dispatch_qty_kgs_pcs;
	}

	public void setDispatch_qty_kgs_pcs(double dispatch_qty_kgs_pcs) {
		this.dispatch_qty_kgs_pcs = dispatch_qty_kgs_pcs;
	}

	public double getRate_dispatch() {
		return rate_dispatch;
	}

	public void setRate_dispatch(double rate_dispatch) {
		this.rate_dispatch = rate_dispatch;
	}

	public double getDispatch_value() {
		return dispatch_value;
	}

	public void setDispatch_value(double dispatch_value) {
		this.dispatch_value = dispatch_value;
	}

	public double getDispatch_qty_pcs_set() {
		return dispatch_qty_pcs_set;
	}

	public void setDispatch_qty_pcs_set(double dispatch_qty_pcs_set) {
		this.dispatch_qty_pcs_set = dispatch_qty_pcs_set;
	}

	public double getRate_dispatch_set() {
		return rate_dispatch_set;
	}

	public void setRate_dispatch_set(double rate_dispatch_set) {
		this.rate_dispatch_set = rate_dispatch_set;
	}

	public double getDispatch_value_set() {
		return dispatch_value_set;
	}

	public void setDispatch_value_set(double dispatch_value_set) {
		this.dispatch_value_set = dispatch_value_set;
	}

	public double getAchievmnet_qty_kgs() {
		return achievmnet_qty_kgs;
	}

	public void setAchievmnet_qty_kgs(double achievmnet_qty_kgs) {
		this.achievmnet_qty_kgs = achievmnet_qty_kgs;
	}

	public double getAchievmnet_qty_kgs_pcs() {
		return achievmnet_qty_kgs_pcs;
	}

	public void setAchievmnet_qty_kgs_pcs(double achievmnet_qty_kgs_pcs) {
		this.achievmnet_qty_kgs_pcs = achievmnet_qty_kgs_pcs;
	}

	public double getAchievment_kg_value() {
		return achievment_kg_value;
	}

	public void setAchievment_kg_value(double achievment_kg_value) {
		this.achievment_kg_value = achievment_kg_value;
	}

	public double getAchievment_qty_pcs_set() {
		return achievment_qty_pcs_set;
	}

	public void setAchievment_qty_pcs_set(double achievment_qty_pcs_set) {
		this.achievment_qty_pcs_set = achievment_qty_pcs_set;
	}

	public double getAchievment_set_value() {
		return achievment_set_value;
	}

	public void setAchievment_set_value(double achievment_set_value) {
		this.achievment_set_value = achievment_set_value;
	}

	
	
}
