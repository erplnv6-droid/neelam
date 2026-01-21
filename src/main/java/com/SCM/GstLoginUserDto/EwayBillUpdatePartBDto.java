package com.SCM.GstLoginUserDto;

public class EwayBillUpdatePartBDto {

	private long ewbNo;
	
	private String vehicleNo;
	
	private String fromPlace;
	
	private int fromState;
	
	private String reasonCode;
	
	private String reasonRem;
	
	private String transDocNo;
	
	private String transDocDate;
	
	private String transMode;
	
	private String vehicleType;

	public EwayBillUpdatePartBDto(long ewbNo, String vehicleNo, String fromPlace, int fromState, String reasonCode,
			String reasonRem, String transDocNo, String transDocDate, String transMode, String vehicleType) {
		super();
		this.ewbNo = ewbNo;
		this.vehicleNo = vehicleNo;
		this.fromPlace = fromPlace;
		this.fromState = fromState;
		this.reasonCode = reasonCode;
		this.reasonRem = reasonRem;
		this.transDocNo = transDocNo;
		this.transDocDate = transDocDate;
		this.transMode = transMode;
		this.vehicleType = vehicleType;
	}

	public EwayBillUpdatePartBDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getEwbNo() {
		return ewbNo;
	}

	public void setEwbNo(long ewbNo) {
		this.ewbNo = ewbNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public int getFromState() {
		return fromState;
	}

	public void setFromState(int fromState) {
		this.fromState = fromState;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonRem() {
		return reasonRem;
	}

	public void setReasonRem(String reasonRem) {
		this.reasonRem = reasonRem;
	}

	public String getTransDocNo() {
		return transDocNo;
	}

	public void setTransDocNo(String transDocNo) {
		this.transDocNo = transDocNo;
	}

	public String getTransDocDate() {
		return transDocDate;
	}

	public void setTransDocDate(String transDocDate) {
		this.transDocDate = transDocDate;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	
}
