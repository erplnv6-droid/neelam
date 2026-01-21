package com.SCM.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="projectionentry")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectionEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@ManyToOne
	@JoinColumn(name="groupn1")
	private Groupn1 groupn1;
	
	@ManyToOne
	@JoinColumn(name="groupn2")
	private Groupn2 groupn2;
	
	@ManyToOne
	@JoinColumn(name="groupn3")
	private Groupn3 groupn3;
	
	@ManyToOne
	@JoinColumn(name="groupn4")
	private Groupn4 groupn4;
	
	@ManyToOne
	@JoinColumn(name="groupn5")
	private Groupn5 groupn5;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "projectionentryitem")
	private List<ProjectionEntryItems> projectionentryitem;
	
	private Date projectionDate;
	
	
}
