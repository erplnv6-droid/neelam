package com.SCM.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.SCM.IndexDto.SalesExpenseReportItemsDTO;
import com.SCM.model.SalesExpenseImages;
import com.SCM.model.SalesExpenseItems;

import lombok.Data;

@Data
public class SalesExpenseDTO {

	private int id;
	private String staffid;
	private String hometown;
	private double dailyallownces;
	private double totalexp;
	private String allowancestatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expdate;
	private String remarks;
	private String salesexpfilename;
	private String salesexpfilelocation;
	private String rsmRemarks;
	private String adminRemarks;
	private List<SalesExpenseItems> salesExpenseItems;
	private List<SalesExpenseImages> salesExpenseImages;
	private String staffname;
	private String designation;
	private double travelfaretotal;
	private double otherexpamounttotal;	
	private List<SalesExpenseReportItemsDTO> expenseItemsDTOList;
}
