package com.SCM.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SalesExpense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String staffid;
	private String hometown;
	private double dailyallownces;
	private String allowancestatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expdate;
	private String status;
	private String rsmRemarks;
	private String adminRemarks;
	private String staffname;
	private String designation;
	private double totalexp;
	private double travelfaretotal;
	private double otherexpamounttotal;
	
	private long createdbyrsmid;
	private String createbyrsmname;
	private long createdbyadminid;
	private String createbyadminname;
	
		
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "salesexp_id")
	private List<SalesExpenseItems> salesExpenseItems;
}
