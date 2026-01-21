package com.SCM.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VoucherMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private String vouchername;
	
@ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
	private Voucher voucher;
	
	private String subtype;
	private int startingnumber;
	private int width;
	private String prefil;
	private LocalDate prefixapplicabledate;
	private String prefixparticular;
	private LocalDate suffixapplicabledate;
	private String suffixparticular;
	private LocalDate restartapplicabledate;
	private int restartnumber;
	private String status;

	
	
}
