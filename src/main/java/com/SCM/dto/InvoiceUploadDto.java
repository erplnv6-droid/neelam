package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.SCM.model.Distributor;

import lombok.Data;

@Data
public class InvoiceUploadDto {

	private int id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate voucherdate;
	private String voucherno;
	private float amount;
	private String imgname;
	private String pdfname;
	private String imglocation;
	private String pdflocation;

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
	private Distributor distributor;
}
