package com.SCM.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.dto.RetailerTemporaryAddressDto;
import com.SCM.mapper.RetailerTemporaryAddressMapper;
import com.SCM.model.RetailerTemporaryAddress;
import com.SCM.repository.RetailerTemporaryAddressRepository;
import com.SCM.service.RetailerTempAddressService;

@Service
public class RetailerTempAddressServiceImpl implements RetailerTempAddressService{
	
	@Autowired
	private RetailerTemporaryAddressRepository retailerTemporaryAddressRepository;
	
	@Autowired
	private RetailerTemporaryAddressMapper retailerTemporaryAddressMapper;

	@Override
	public List<RetailerTemporaryAddressDto> findRetailerTempAddByRTId(Long id) {
		
		List<RetailerTemporaryAddress> byRTId = retailerTemporaryAddressRepository.findRetailerTempAddByRTId(id);
		
		return byRTId.stream().map(retailerTemporaryAddressMapper :: mapToDto).collect(Collectors.toList());
	}

	@Override
	public String deleteById(Long id) {
		
		retailerTemporaryAddressRepository.deleteById(id);
		
		return "";
	}

}
