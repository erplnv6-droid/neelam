package com.SCM.dto;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.SCM.model.Groupn1;
import com.SCM.model.Groupn2;
import com.SCM.model.Groupn3;
import com.SCM.model.Groupn4;
import com.SCM.model.Groupn5;
import com.SCM.model.ProjectionEntryItems;

import lombok.Data;

@Data
public class ProjectionEntryDto {

  	private long id;

	private Groupn1 groupn1;

	private Groupn2 groupn2;
	
	private Groupn3 groupn3;

	private Groupn4 groupn4;

	private Groupn5 groupn5;
	
	private Date projectionDate;


	private List<ProjectionEntryItems> projectionentryitem;
}
