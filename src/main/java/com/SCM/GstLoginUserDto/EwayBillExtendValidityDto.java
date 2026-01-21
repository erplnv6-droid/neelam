package com.SCM.GstLoginUserDto;

public class EwayBillExtendValidityDto {

	private long ewbNo;
	
	private String vehicleNo;
	
	private String fromPlace;
	
	private int fromState;
	
	private int remainingDistance;
	
	private String transDocNo;
	
	private String transDocDate;
	
	private String transMode;
	
	private int extnRsnCode;
	
	private String extnRemarks;
	
	private int fromPincode;
	
	private String consignmentStatus;
	
	private String transitType;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String addressLine3;

	public EwayBillExtendValidityDto(long ewbNo, String vehicleNo, String fromPlace, int fromState,
			int remainingDistance, String transDocNo, String transDocDate, String transMode, int extnRsnCode,
			String extnRemarks, int fromPincode, String consignmentStatus, String transitType, String addressLine1,
			String addressLine2, String addressLine3) {
		super();
		this.ewbNo = ewbNo;
		this.vehicleNo = vehicleNo;
		this.fromPlace = fromPlace;
		this.fromState = fromState;
		this.remainingDistance = remainingDistance;
		this.transDocNo = transDocNo;
		this.transDocDate = transDocDate;
		this.transMode = transMode;
		this.extnRsnCode = extnRsnCode;
		this.extnRemarks = extnRemarks;
		this.fromPincode = fromPincode;
		this.consignmentStatus = consignmentStatus;
		this.transitType = transitType;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
	}

	public EwayBillExtendValidityDto() {
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

	public int getRemainingDistance() {
		return remainingDistance;
	}

	public void setRemainingDistance(int remainingDistance) {
		this.remainingDistance = remainingDistance;
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

	public int getExtnRsnCode() {
		return extnRsnCode;
	}

	public void setExtnRsnCode(int extnRsnCode) {
		this.extnRsnCode = extnRsnCode;
	}

	public String getExtnRemarks() {
		return extnRemarks;
	}

	public void setExtnRemarks(String extnRemarks) {
		this.extnRemarks = extnRemarks;
	}

	public int getFromPincode() {
		return fromPincode;
	}

	public void setFromPincode(int fromPincode) {
		this.fromPincode = fromPincode;
	}

	public String getConsignmentStatus() {
		return consignmentStatus;
	}

	public void setConsignmentStatus(String consignmentStatus) {
		this.consignmentStatus = consignmentStatus;
	}

	public String getTransitType() {
		return transitType;
	}

	public void setTransitType(String transitType) {
		this.transitType = transitType;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	
	
}
