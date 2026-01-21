package com.SCM.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import com.SCM.model.Distributor;
import com.SCM.model.Retailer;
import com.SCM.model.Staff;

import lombok.Data;

@Data
public class MinutesOfMeetingDto {

	private int id;
	private String meetingtitle;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate meetingdate;
	private String description;
	private String docname;
	private String doclocation;
	private List<Staff> staff;
	private String stringimage;
	private Retailer retailer;
	private Distributor distributor;
	
}
