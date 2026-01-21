package com.SCM.GstLoginUserDto;

import java.util.List;



import com.SCM.GstDto.EwayItem;


public class EwayBillPartBGenerateDto {

private String supplyType;
	
	private String subSupplyType;
	
	private String subSupplyDesc;
	
	private String docType;
	
	private String docNo;
	
	private String docDate;
	
	private String fromGstin;
	
	private String fromTrdName;
	
	private String fromAddr1;
	
	private String fromAddr2;
	
	private String fromPlace;
	
	private int fromPincode;
	
	private int actFromStateCode;
	
	private int fromStateCode;
	
	private String toGstin;
	
	private String toTrdName;
	
	private String toAddr1;
	
	private String toAddr2;
	
	private String toPlace;
	
	private int toPincode;
	
	private int actToStateCode;
	
	private int toStateCode;
	
	private int transactionType;
	
	private int otherValue;
	
	private double totalValue;
	
	private double cgstValue;
	
	private double sgstValue;
	
	private double igstValue;
	
	private double cessValue;
	
	private double cessNonAdvolValue;
	
    private double totInvValue;
	
	private String transporterId;
	
	private String transporterName;
	
	private String transDocNo;
	
	private String transMode;
	
	private String transDistance;
	
	private String transDocDate;
	
	private String vehicleNo;
	
	private String vehicleType;
	
	private List<EwayItem> itemList;

	public EwayBillPartBGenerateDto(String supplyType, String subSupplyType, String subSupplyDesc, String docType,
			String docNo, String docDate, String fromGstin, String fromTrdName, String fromAddr1, String fromAddr2,
			String fromPlace, int fromPincode, int actFromStateCode, int fromStateCode, String toGstin,
			String toTrdName, String toAddr1, String toAddr2, String toPlace, int toPincode, int actToStateCode,
			int toStateCode, int transactionType, int otherValue, double totalValue, double cgstValue, double sgstValue,
			double igstValue, double cessValue, double cessNonAdvolValue, double totInvValue, String transporterId,
			String transporterName, String transDocNo, String transMode, String transDistance, String transDocDate,
			String vehicleNo, String vehicleType, List<EwayItem> itemList) {
		super();
		this.supplyType = supplyType;
		this.subSupplyType = subSupplyType;
		this.subSupplyDesc = subSupplyDesc;
		this.docType = docType;
		this.docNo = docNo;
		this.docDate = docDate;
		this.fromGstin = fromGstin;
		this.fromTrdName = fromTrdName;
		this.fromAddr1 = fromAddr1;
		this.fromAddr2 = fromAddr2;
		this.fromPlace = fromPlace;
		this.fromPincode = fromPincode;
		this.actFromStateCode = actFromStateCode;
		this.fromStateCode = fromStateCode;
		this.toGstin = toGstin;
		this.toTrdName = toTrdName;
		this.toAddr1 = toAddr1;
		this.toAddr2 = toAddr2;
		this.toPlace = toPlace;
		this.toPincode = toPincode;
		this.actToStateCode = actToStateCode;
		this.toStateCode = toStateCode;
		this.transactionType = transactionType;
		this.otherValue = otherValue;
		this.totalValue = totalValue;
		this.cgstValue = cgstValue;
		this.sgstValue = sgstValue;
		this.igstValue = igstValue;
		this.cessValue = cessValue;
		this.cessNonAdvolValue = cessNonAdvolValue;
		this.totInvValue = totInvValue;
		this.transporterId = transporterId;
		this.transporterName = transporterName;
		this.transDocNo = transDocNo;
		this.transMode = transMode;
		this.transDistance = transDistance;
		this.transDocDate = transDocDate;
		this.vehicleNo = vehicleNo;
		this.vehicleType = vehicleType;
		this.itemList = itemList;
	}

	public EwayBillPartBGenerateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(String supplyType) {
		this.supplyType = supplyType;
	}

	public String getSubSupplyType() {
		return subSupplyType;
	}

	public void setSubSupplyType(String subSupplyType) {
		this.subSupplyType = subSupplyType;
	}

	public String getSubSupplyDesc() {
		return subSupplyDesc;
	}

	public void setSubSupplyDesc(String subSupplyDesc) {
		this.subSupplyDesc = subSupplyDesc;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public String getFromGstin() {
		return fromGstin;
	}

	public void setFromGstin(String fromGstin) {
		this.fromGstin = fromGstin;
	}

	public String getFromTrdName() {
		return fromTrdName;
	}

	public void setFromTrdName(String fromTrdName) {
		this.fromTrdName = fromTrdName;
	}

	public String getFromAddr1() {
		return fromAddr1;
	}

	public void setFromAddr1(String fromAddr1) {
		this.fromAddr1 = fromAddr1;
	}

	public String getFromAddr2() {
		return fromAddr2;
	}

	public void setFromAddr2(String fromAddr2) {
		this.fromAddr2 = fromAddr2;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public int getFromPincode() {
		return fromPincode;
	}

	public void setFromPincode(int fromPincode) {
		this.fromPincode = fromPincode;
	}

	public int getActFromStateCode() {
		return actFromStateCode;
	}

	public void setActFromStateCode(int actFromStateCode) {
		this.actFromStateCode = actFromStateCode;
	}

	public int getFromStateCode() {
		return fromStateCode;
	}

	public void setFromStateCode(int fromStateCode) {
		this.fromStateCode = fromStateCode;
	}

	public String getToGstin() {
		return toGstin;
	}

	public void setToGstin(String toGstin) {
		this.toGstin = toGstin;
	}

	public String getToTrdName() {
		return toTrdName;
	}

	public void setToTrdName(String toTrdName) {
		this.toTrdName = toTrdName;
	}

	public String getToAddr1() {
		return toAddr1;
	}

	public void setToAddr1(String toAddr1) {
		this.toAddr1 = toAddr1;
	}

	public String getToAddr2() {
		return toAddr2;
	}

	public void setToAddr2(String toAddr2) {
		this.toAddr2 = toAddr2;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public int getToPincode() {
		return toPincode;
	}

	public void setToPincode(int toPincode) {
		this.toPincode = toPincode;
	}

	public int getActToStateCode() {
		return actToStateCode;
	}

	public void setActToStateCode(int actToStateCode) {
		this.actToStateCode = actToStateCode;
	}

	public int getToStateCode() {
		return toStateCode;
	}

	public void setToStateCode(int toStateCode) {
		this.toStateCode = toStateCode;
	}

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	public int getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(int otherValue) {
		this.otherValue = otherValue;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public double getCgstValue() {
		return cgstValue;
	}

	public void setCgstValue(double cgstValue) {
		this.cgstValue = cgstValue;
	}

	public double getSgstValue() {
		return sgstValue;
	}

	public void setSgstValue(double sgstValue) {
		this.sgstValue = sgstValue;
	}

	public double getIgstValue() {
		return igstValue;
	}

	public void setIgstValue(double igstValue) {
		this.igstValue = igstValue;
	}

	public double getCessValue() {
		return cessValue;
	}

	public void setCessValue(double cessValue) {
		this.cessValue = cessValue;
	}

	public double getCessNonAdvolValue() {
		return cessNonAdvolValue;
	}

	public void setCessNonAdvolValue(double cessNonAdvolValue) {
		this.cessNonAdvolValue = cessNonAdvolValue;
	}

	public double getTotInvValue() {
		return totInvValue;
	}

	public void setTotInvValue(double totInvValue) {
		this.totInvValue = totInvValue;
	}

	public String getTransporterId() {
		return transporterId;
	}

	public void setTransporterId(String transporterId) {
		this.transporterId = transporterId;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getTransDocNo() {
		return transDocNo;
	}

	public void setTransDocNo(String transDocNo) {
		this.transDocNo = transDocNo;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	public String getTransDistance() {
		return transDistance;
	}

	public void setTransDistance(String transDistance) {
		this.transDistance = transDistance;
	}

	public String getTransDocDate() {
		return transDocDate;
	}

	public void setTransDocDate(String transDocDate) {
		this.transDocDate = transDocDate;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public List<EwayItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<EwayItem> itemList) {
		this.itemList = itemList;
	}

	

	


	
	
	
	
}
