package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="group6")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Group6 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String title;

	@ManyToOne
	@JoinColumn(name="group1")
	private Group1 group1;
	
	@ManyToOne
	@JoinColumn(name="group2")
	private Group2 group2;
	
	@ManyToOne
	@JoinColumn(name="group3")
	private Group3 group3;
	
	@ManyToOne
	@JoinColumn(name="group4")
	private Group4 group4;
	
	@ManyToOne
	@JoinColumn(name="group5")
	private Group5 group5;
	
	
	private LocalDate createddate;
	private LocalTime createdtime;
	private long createdby;
	private String createbyname;
	private String role;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
}
