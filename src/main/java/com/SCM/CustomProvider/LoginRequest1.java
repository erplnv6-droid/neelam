package com.SCM.CustomProvider;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest1 {
	
	    @NotBlank
	    private String email;
	    @NotBlank
	    private String otp;
}
