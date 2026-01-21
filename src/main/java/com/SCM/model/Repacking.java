package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Repacking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name="bid")
	private Brand brand;
	
	private String brandname;
	
	private String operatorname;
	private LocalDate createddate;
	private LocalTime createdtime;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="repackid")
	private List<RepackingItems> repackingItems;
	
}
