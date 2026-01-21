package com.SCM.IndexDto;

import java.time.LocalDate;

public interface IndexVoucherMaster {
	
	Long getId();
	
	String getvoucherseries();
	
	String getSubtype();
	
	Integer getStartingnumber();
	
	Integer getWidth();
	
	String getPrefil();
	
	LocalDate getPrefixapplicabledate();
	
	String getPrefixparticular();
	
	LocalDate getSuffixapplicabledate();
	
	String getSuffixparticular();
	
	LocalDate getRestartapplicabledate();
	
	Integer getRestartnumber();
	
	String getStatus();
	
	
	
}
