package com.SCM.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Loc2DTO {

	private int id;
	private double lattitude;
	private double longitude;
	private LocalDate timestamp;
	private int staffid;
	private String staffname;
}
