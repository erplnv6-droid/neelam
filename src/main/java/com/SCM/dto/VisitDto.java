package com.SCM.dto;

import java.time.LocalDateTime;

import com.SCM.model.Schedule;

import lombok.Data;

@Data
public class VisitDto {

	private int id;
	private LocalDateTime visitin;
	private LocalDateTime visitout;
	private Schedule schedule;

	

}
