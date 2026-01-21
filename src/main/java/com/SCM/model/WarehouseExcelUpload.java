package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class WarehouseExcelUpload {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String productname;
    private String dlppcs;
    private String dlpkg;

}
