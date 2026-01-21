package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.SCM.model.Distributor;
import com.SCM.model.DistributorOpeningStockItems;
import com.SCM.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class DistributorOpeningStockDto {

	private long id;
	private java.sql.Date user_date;
	private double grandtotal;
	private java.sql.Date date;
	private Integer quantity;
	private String uom;

	private Product product;

	private Distributor distributor;

	private List<DistributorOpeningStockItems> distributorOpeningStockItems;

	private Date lastUpdate;

	private long createdby;
	private String createbyname;
	private String role;

	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;

}
