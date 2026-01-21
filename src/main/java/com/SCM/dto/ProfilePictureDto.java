package com.SCM.dto;

import lombok.Data;

@Data
public class ProfilePictureDto {

	private int id;
	private String profilepicname;
	private String profilepiclocation;
	
	private int staffId;
	private int retailerId;
	private int distributorId;
	private int supplierId;
}
