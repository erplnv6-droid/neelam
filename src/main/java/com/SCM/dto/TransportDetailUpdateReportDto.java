package com.SCM.dto;

import java.sql.Date;

public class TransportDetailUpdateReportDto {
	
	
	private Date voucherDate;
	
	private String voucherNumber;
	
	private String partyName;
	
	private String destination;
	
	private double amount;
	
	private String transporter;
	
	private double transportCharges;
	
	private float freightCost;
	
	private String lrNumber;
	
	private Date lrnDate;
	
	private String edd;
	
	private String grnNumber;
	
	private String grnDate;
	
	private long earlyLateDelivery;
	
	private String totalNoOfPKGS;
	
	private Date start_date;
	
	private Date end_date;

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public double getTransportCharges() {
		return transportCharges;
	}

	public void setTransportCharges(double transportCharges) {
		this.transportCharges = transportCharges;
	}

	public double getFreightCost() {
		return freightCost;
	}

   

	public void setFreightCost(float freightCost) {
		this.freightCost = freightCost;
	}

	public String getLrNumber() {
		return lrNumber;
	}

	public void setLrNumber(String lrNumber) {
		this.lrNumber = lrNumber;
	}

	public Date getLrnDate() {
		return lrnDate;
	}

	public void setLrnDate(Date lrnDate) {
		this.lrnDate = lrnDate;
	}

	public String getEdd() {
		return edd;
	}

	public void setEdd(String edd) {
		this.edd = edd;
	}

	public String getGrnNumber() {
		return grnNumber;
	}

	public void setGrnNumber(String grnNumber) {
		this.grnNumber = grnNumber;
	}

	public String getGrnDate() {
		return grnDate;
	}

	public void setGrnDate(String grnDate) {
		this.grnDate = grnDate;
	}

	public long getEarlyLateDelivery() {
		return earlyLateDelivery;
	}

	public void setEarlyLateDelivery(long earlyLateDelivery) {
		this.earlyLateDelivery = earlyLateDelivery;
	}

	
	public String getTotalNoOfPKGS() {
		return totalNoOfPKGS;
	}

	public void setTotalNoOfPKGS(String totalNoOfPKGS) {
		this.totalNoOfPKGS = totalNoOfPKGS;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	
	

	public TransportDetailUpdateReportDto(Date voucherDate, String voucherNumber, String partyName, String destination,
			double amount, String transporter, double transportCharges, float freightCost, String lrNumber,
			Date lrnDate, String edd, String grnNumber, String grnDate, long earlyLateDelivery, String totalNoOfPKGS,
			Date start_date, Date end_date) {
		super();
		this.voucherDate = voucherDate;
		this.voucherNumber = voucherNumber;
		this.partyName = partyName;
		this.destination = destination;
		this.amount = amount;
		this.transporter = transporter;
		this.transportCharges = transportCharges;
		this.freightCost = freightCost;
		this.lrNumber = lrNumber;
		this.lrnDate = lrnDate;
		this.edd = edd;
		this.grnNumber = grnNumber;
		this.grnDate = grnDate;
		this.earlyLateDelivery = earlyLateDelivery;
		this.totalNoOfPKGS = totalNoOfPKGS;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public TransportDetailUpdateReportDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
