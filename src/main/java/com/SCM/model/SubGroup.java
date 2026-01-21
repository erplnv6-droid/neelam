package com.SCM.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="subgroup")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private long subgroupid;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="groupid")
	private Group groupid;

	
	

}
