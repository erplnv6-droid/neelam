package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class CartonBarcodeActivityLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate createddate;
	private LocalTime createdtime;
	private String unqno;
	private String loggeduser;
	private Long cartonbarcodeid;
}
