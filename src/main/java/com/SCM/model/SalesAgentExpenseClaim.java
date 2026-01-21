package com.SCM.model;

import java.sql.Date;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="salesagentexpenseclaim")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesAgentExpenseClaim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(updatable = false)
	public Date createdOnDate;
	
//	===============================
	
    @Column(updatable = false)
	public LocalTime createdTime;
	
	public LocalTime time;
	
//	===============================
	public Date date;
	
	public long salesStaffId;
	
	public String remark;
	public String status;
	private String filepath;
	private String imagename;
	private long claimexpense;
	private long approvedexpense;
		
}
