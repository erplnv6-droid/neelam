package com.SCM.IndexDto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class SalesExpenseReportItemsDTO {

	private String travelfrom;

	private String travelto;

	private String modeoftravel;

	private double travelfare;

	private double otherexpamount;

	private String otherexp;
	
	private double acceptedamountbyadmin;
	
	private double acceptedamountbyrsm;
	
	private double sumapprovedexpensebyadmin;
	
	private double sumapprovedexpensebyrsm;
}
