package com.SCM.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class SalesAgentExpenseClaimDto {

	
	private long id;
	public Date createdOnDate;
	public Date date;
	public long salesStaffId;
	public String remark;
	public String status;
	private String filepath;
	private String imagename;
	private long claimexpense;
	private long approvedexpense;
	
}
