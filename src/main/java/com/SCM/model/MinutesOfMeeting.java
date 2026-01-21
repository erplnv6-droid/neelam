package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MinutesOfMeeting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String meetingtitle;
	private LocalDate meetingdate;
	
	@Size(max = 10000000)
	private String description;
	private String docname;
	private String doclocation;
	private LocalDate createddate;
	private LocalTime createdtime;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long createdby;
	private String createbyname;
	private String role;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "minutesofmeeting_id")
	private List<Staff> staff;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinColumn(name = "minutesofmeeting_id")
	private List<Retailer> retailer;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinColumn(name = "minutesofmeeting_id")
	private List<Distributor> distributor;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinColumn(name = "minutesofmeeting_id")
	private List<Supplier> suppliers;
	
	
}
