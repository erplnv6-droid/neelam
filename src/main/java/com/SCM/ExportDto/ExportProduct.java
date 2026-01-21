package com.SCM.ExportDto;

public interface ExportProduct {

	Integer getId();

	String getProduct_Name();

	String getShort_Name();

	String getEan_Code();

	String getStandard_Qty_Per_Box();

	String getCategory();

	String getUom_Primary();

	String getUom_Secondary();

	String getMrp();

	String getCapacity();

	String getDiameter();

	String getHsn_Code();

	String getBrand();

	String getIgst();

	String getCgst();

	String getSgst();

	Integer getIgst_Ledger();

	Integer getCgst_Ledger();

	Integer getSgst_Ledger();

	String getProduct_Type();

	String getProduct_Group();

	Float getDlp();
}
