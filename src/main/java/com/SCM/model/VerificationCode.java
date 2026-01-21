package com.SCM.model;

import java.time.Instant;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="verificationCodes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCode {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private int otp;
	private Instant time;
	
}
