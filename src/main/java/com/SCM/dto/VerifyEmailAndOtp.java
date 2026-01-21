package com.SCM.dto;

import lombok.Data;

@Data
public class VerifyEmailAndOtp {

	private String email;
	private int otp;
}
