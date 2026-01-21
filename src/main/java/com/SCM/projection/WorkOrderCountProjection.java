package com.SCM.projection;

public interface WorkOrderCountProjection {

	Integer getTotal_pending_count();
	
	Integer getTotal_converted_count();
	
	Integer getTotal_count();
	
	Integer getTotal_amount();

}
