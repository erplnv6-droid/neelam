package com.SCM.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.dto.DistributorAddressDto;
import com.SCM.dto.DistributorAddressMapper;
import com.SCM.model.DistributorAddress;
import com.SCM.repository.DistributorAddressRepository;
import com.SCM.service.DistributorAddressService;

@Service
public class DistributorAddressServiceImpl implements DistributorAddressService{
	
	@Autowired
	private DistributorAddressRepository distributorAddressRepository;
	
	@Autowired
	private DistributorAddressMapper distributorAddressMapper;
	
	

	@Override
	public List<DistributorAddressDto> findDistributorAddressByDistributor(Long id) {
		
		List<DistributorAddress> byDistributor = distributorAddressRepository.findDistributorAddressByDistributor(id);
		
		return byDistributor.stream().map(distributorAddressMapper :: mapToDistributorAddressDto).collect(Collectors.toList());
	}

	@Override
	public String deleteByDistributorAddressId(Long id) {
		
		distributorAddressRepository.deleteById(id);
		
		return "";
	}

}
