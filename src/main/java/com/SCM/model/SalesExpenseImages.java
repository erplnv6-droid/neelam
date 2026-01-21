package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SalesExpenseImages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String salesexpfilename;
	private String salesexpfilelocation;
	

	@OneToOne
	@JoinColumn(name = "salesexpitems_id")
	private SalesExpenseItems salesExpenseItems;
}
