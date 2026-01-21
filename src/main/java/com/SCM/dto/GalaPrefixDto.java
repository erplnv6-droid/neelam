package com.SCM.dto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.model.Staff;

import lombok.Data;

@Data
public class GalaPrefixDto {

	private long id;
	private String gname;
	
	private Staff staff;
}
