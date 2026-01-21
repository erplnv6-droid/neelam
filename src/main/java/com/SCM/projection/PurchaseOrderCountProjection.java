package com.SCM.projection;

public interface PurchaseOrderCountProjection {

Integer getTotal_pending_count();
	
	Integer getTotal_converted_count();
	
	Integer getTotal_count();
	
	Double getTotal_amount();
}
