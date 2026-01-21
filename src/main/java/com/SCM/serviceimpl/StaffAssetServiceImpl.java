package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.dto.StaffAssetDto;
import com.SCM.mapper.StaffAssetMapper;
import com.SCM.model.StaffAsset;
import com.SCM.repository.StaffAssetRepository;
import com.SCM.service.StaffAssetService;

@Service
public class StaffAssetServiceImpl implements StaffAssetService{

	@Autowired
	private StaffAssetRepository staffAssetRepository;
	
	@Autowired
	private StaffAssetMapper staffAssetMapper;
	
	
	public long getUserId() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
			String username = userDetails.getUsername();
			Long uid = userDetails.getId();

			return uid;
	}
	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return username;
	}

	public String getRolename() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		String role=list.get(0);
		return role;
	}
	
	
	
	@Override
	public StaffAssetDto save(StaffAssetDto staffAssetDto) {
		// TODO Auto-generated method stub

		Long uid = getUserId();
		String uname=getUserName();

		String role=getRolename();
		
		StaffAsset entity = staffAssetMapper.toEntity(staffAssetDto);
		entity.setCreatedby(uid);
		entity.setCreatedbyname(uname);
		entity.setRole(role);
		entity.setCreateddate(LocalDate.now());
		entity.setCreatedtime(LocalTime.now());
		
		StaffAsset save = staffAssetRepository.save(entity);
		StaffAssetDto dto = staffAssetMapper.toDto(save);
		return dto;
		
	}

	@Override
	public String updateStaffAsset(long id, StaffAssetDto assetDto) {
		// TODO Auto-generated method stub

		Long uid = getUserId();
		String uname=getUserName();

		String role=getRolename();
		if (staffAssetRepository.existsById(id)) {
			
			StaffAsset entity = staffAssetMapper.toEntity(assetDto);
			entity.setId(id);
			entity.setUpdatedby(uid);
			entity.setUpdatedbyname(uname);
			entity.setUpdateddate(LocalDate.now());
			entity.setUpdatedrole(role);
			entity.setUpdatedtime(LocalTime.now());
			
			StaffAsset save = staffAssetRepository.save(entity);
			StaffAssetDto dto = staffAssetMapper.toDto(save);
			return "Succesfully Updated the StaffAsset with id"+id;
		}
		return "No Staff Asset is Present With Id "+id;
	}

	@Override
	public Optional<StaffAssetDto> getById(long id) {
		// TODO Auto-generated method stub
		Optional<StaffAsset> findById = staffAssetRepository.findById(id);
		Optional<StaffAssetDto> map = findById.map((sa)-> staffAssetMapper.toDto(sa));
		return map;
	}

	
	@Override
	public List<StaffAssetDto> getAll() {
		// TODO Auto-generated method stub
		List<StaffAsset> findAll = staffAssetRepository.findAll();
		List<StaffAssetDto> collect = findAll.stream().map((sa)-> staffAssetMapper.toDto(sa)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public String deleteStaffAsset(long id) {
		// TODO Auto-generated method stub
		if (staffAssetRepository.existsById(id)) {
			staffAssetRepository.deleteById(id);
			return "Succesfully deleted the staff asset with id "+id;
		}
		return "No Staff Asset is Present With Id "+id;
	}

	
	
}
