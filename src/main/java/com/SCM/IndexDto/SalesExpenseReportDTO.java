package com.SCM.IndexDto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.SCM.dto.SalesExpenseDTO;

import lombok.Data;

@Data
@Component
public class SalesExpenseReportDTO {

	private int id;
	private String staff_name;
	private String designation;
	private String hometown;
	private String expdate;
	private double dailyallownces;
	private double totalexp;
	private String approvalbyadmin;
	private double approvedexpensebyadmin;
	
	private Double countvisit;
	private double secondaryordervalue;
	private String otherexpamounttotal;
	private double grandtotalfare;
	private double grandtotalotherexpense;
	private double grandtotaldailyallowances;
	private double grandtotalexpense;
	private double grandtotalcountvisit;
	private double grandtotalsecondaryvalue;
	private double grandtotalapprovedexpensebyadmin;
	private double grandtotalapprovedexpensebyrsm;
	private double daytotalexpense;
	private Double totalcountvisit;
	private Double totalsecondaryvalue;

	
	private String status;
	
	private List<SalesExpenseReportItemsDTO> expenseReportItemsDTO;	
}
