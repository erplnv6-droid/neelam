package com.SCM.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime visitin;
	private LocalDateTime visitout;

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Schedule schedule;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getVisitin() {
		return visitin;
	}

	public void setVisitin(LocalDateTime visitin) {
		this.visitin = visitin;
	}

	public LocalDateTime getVisitout() {
		return visitout;
	}

	public void setVisitout(LocalDateTime visitout) {
		this.visitout = visitout;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

}
