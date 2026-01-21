
package com.SCM.serviceimpl;

import org.springframework.stereotype.Service;

import com.SCM.dto.ZoneDto;
import com.SCM.model.Zone;
import com.SCM.repository.ZoneRepo;
import com.SCM.service.ZoneService;

@Service
public class ZoneServiceImpl implements ZoneService {
	
	private ZoneRepo zoneRepo;
	
	public ZoneServiceImpl(ZoneRepo zoneRepo) {
		super();
		this.zoneRepo = zoneRepo;
	}

	@Override
	public ZoneDto getZoneByStaffId(Long id) {
		
		ZoneDto zoneDto = new ZoneDto();
		Zone zone = zoneRepo.getZoneBystaffId(id);
		
		zoneDto.setId(zone.getId());
		zoneDto.setZoneName(zone.getZoneName());
		zoneDto.setState_zone(zone.getState_zone());
		
		return zoneDto;
	}

}
