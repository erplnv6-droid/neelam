package com.SCM.serviceimpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.dto.Loc2DTO;
import com.SCM.model.Loc2;
import com.SCM.repository.LocRepository;
import com.SCM.service.LocService;

@Service
public class LocServiceImpl implements LocService {

	@Autowired
	private LocRepository locRepository;

	@Override
	public Loc2 save(Loc2DTO loc2dto) {

		Loc2 bystaffid = locRepository.findBystaffid(loc2dto.getStaffid());

		Loc2 l = new Loc2();

		if (bystaffid != null) {
			
			locRepository.deletebystaffid(bystaffid.getStaffid());

			l.setStaffid(loc2dto.getStaffid());
			l.setStaffname(loc2dto.getStaffname());
			l.setTimestamp(LocalDate.now());
			l.setLattitude(loc2dto.getLattitude());
			l.setLongitude(loc2dto.getLongitude());

			locRepository.save(l);
		} else {

			l.setStaffid(loc2dto.getStaffid());
			l.setStaffname(loc2dto.getStaffname());
			l.setTimestamp(LocalDate.now());
			l.setLattitude(loc2dto.getLattitude());
			l.setLongitude(loc2dto.getLongitude());
		}
		Loc2 save = locRepository.save(l);

		return save;
	}

	@Override
	public Loc2 getbystaffid(int staffid) {
		
		Loc2 bystaffid = locRepository.findBystaffid(staffid);
		
		return bystaffid;
	}

}
