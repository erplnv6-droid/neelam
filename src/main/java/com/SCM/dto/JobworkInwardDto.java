package com.SCM.dto;

import java.sql.Date;
import java.util.List;

import com.SCM.model.JobworkInwardItems;
import com.SCM.model.Supplier;
import com.SCM.model.Warehouse;

import lombok.Data;

@Data
public class JobworkInwardDto {

	private int id;
	private String jobsheetno;
	private Date jobsheetdate;
	private String jobtype;
	private String remarks;
	private float grandtotal;
	private Warehouse warehouse;
	private Supplier supplier;
	private List<JobworkInwardItems> jobsheetItems;
}
