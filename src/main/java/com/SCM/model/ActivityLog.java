package com.SCM.model;

import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class ActivityLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 
	
	@Temporal(TemporalType.DATE)
	private Date createdate;
	
	private LocalTime createdtime;
	
	@Temporal(TemporalType.DATE)
	private Date updatedate;
	
	private LocalTime updatedtime;

	private Long loggeduser;
	
	private Long brandId;
	private Long branchid;
	private Long warehouseid;
	private Long companyid;
	private Long customerid;
	private Long productid;
	private Long supplierid;
	private Long locationid;
	private Long scheduleid;
	private Long staffid;
	private Long visitid;
	private Long distributorid;
	private Long retailerid;
	private Long workorderid;
	private Long primaryorderid;
	private Long purchaseid;
	private Long purchaseorderid;
	private Long purchasereturnid;
	private Long salesid;
	private Long salesreturnid;
	private Long salesorderid;
	private Long deliverychargeid;
	private Long materialreceiptnoteid;
	private Long supplierdeliverynoteid;
		
}