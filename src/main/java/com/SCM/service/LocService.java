package com.SCM.service;

import com.SCM.dto.Loc2DTO;
import com.SCM.model.Loc2;

public interface LocService {

	public Loc2 save(Loc2DTO loc2dto);
	
	public Loc2 getbystaffid(int staffid);
}
