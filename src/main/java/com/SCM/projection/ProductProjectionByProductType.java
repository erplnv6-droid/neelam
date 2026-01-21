package com.SCM.projection;

public interface ProductProjectionByProductType {
	Integer getId();

	String getProduct_name();

	String getLocation();

	String getMrp();

	String getEan_code();

	String getShort_name();

	String getHsn_code();

	String getCategory();

	String getProduct_type();
	
	String getStandard_qty_per_box();
	
	String getUom_primary();
	
	String getUom_secondary();
	
	Float getDlp();
	
	String getCapacity();
	
	String getDiameter();
	
	String getBrand();
	
	String getIgst();
	
	String getCgst();
	
	String getSgst();
}
