package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DistributorToStaff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int aseid;
	private int asmid;
	private int rsmid;
	private int nsmid;
}
