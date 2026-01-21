package com.SCM.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.SCM.model.DistributorSalesReturnItems;

import lombok.Data;;

@Data
public class DistributorSalesReturnDto {

private long id;
	
	private String salesreturnnumber;
	
	private Date salesreturndate;
	
	private String originalinvoiceno;
	
	private Date originalinvoicedate;
	
	private String termsofpayment;
	
	private String otherreference;
	
	private String dispatchdocumentnumber;
	
	private String dispatchthrough;
	
	private String destination;
	
	private String termsofdelivery;

	private List<DistributorSalesReturnItems> distributorSalesReturnItems = new ArrayList<>();
}
