package com.SCM.dto;

import com.SCM.model.Group;

import lombok.Data;

@Data
public class SubGroupDto {

	private long id;
	private String name;
	private long subgroupid;
	private Group groupid;

}
