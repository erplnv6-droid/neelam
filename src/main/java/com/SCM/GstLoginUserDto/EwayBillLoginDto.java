package com.SCM.GstLoginUserDto;

import java.util.List;




import com.SCM.GstDto.ItemList;


public class EwayBillLoginDto {

private String Irn;

private int Distance;

private String TransMode;

private String TransId;

private String TransName;

private String TrnDocDt;

private String TrnDocNo;

private String VehNo;

private String VehType;


private int sales_id;

public EwayBillLoginDto(String irn, int distance, String transMode, String transId, String transName, String trnDocDt,
		String trnDocNo, String vehNo, String vehType, int salesId) {


	super();
	Irn = irn;
	Distance = distance;
	TransMode = transMode;
	TransId = transId;
	TransName = transName;
	TrnDocDt = trnDocDt;
	TrnDocNo = trnDocNo;
	VehNo = vehNo;
	VehType = vehType;

	this.sales_id = salesId;

}

public EwayBillLoginDto() {
	super();
	// TODO Auto-generated constructor stub
}

public String getIrn() {
	return Irn;
}

public void setIrn(String irn) {
	Irn = irn;
}

public int getDistance() {
	return Distance;
}

public void setDistance(int distance) {
	Distance = distance;
}

public String getTransMode() {
	return TransMode;
}

public void setTransMode(String transMode) {
	TransMode = transMode;
}

public String getTransId() {
	return TransId;
}

public void setTransId(String transId) {
	TransId = transId;
}

public String getTransName() {
	return TransName;
}

public void setTransName(String transName) {
	TransName = transName;
}

public String getTrnDocDt() {
	return TrnDocDt;
}

public void setTrnDocDt(String trnDocDt) {
	TrnDocDt = trnDocDt;
}

public String getTrnDocNo() {
	return TrnDocNo;
}

public void setTrnDocNo(String trnDocNo) {
	TrnDocNo = trnDocNo;
}

public String getVehNo() {
	return VehNo;
}

public void setVehNo(String vehNo) {
	VehNo = vehNo;
}

public String getVehType() {
	return VehType;
}

public void setVehType(String vehType) {
	VehType = vehType;
}


public int getSalesId() {
	return sales_id;
}

public void setSalesId(int salesId) {
	this.sales_id = salesId;
}


		
	
	



	
	
	
	
	
}
