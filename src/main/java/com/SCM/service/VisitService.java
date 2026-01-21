package com.SCM.service;

import java.util.List;

import com.SCM.dto.VisitDto;
import com.SCM.model.Visit;

public interface VisitService {

	public Visit visitIn(VisitDto visitDto);
	
	public Visit visitOut(VisitDto visitDto,int id);
	
	public List<Visit> fetchVisit();
}
