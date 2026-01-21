package com.SCM.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SCM.model.DistributorAddress;

@Component
public class DistributorAddressMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public DistributorAddressDto mapToDistributorAddressDto(DistributorAddress distributorAddress)
	{
		DistributorAddressDto distributorAddressDto = modelMapper.map(distributorAddress, DistributorAddressDto.class);
		
		return distributorAddressDto;
	}
	
	
	public DistributorAddress mapToDistributorAddress(DistributorAddressDto distributorAddressDto)
	{
		DistributorAddress distributorAddress = modelMapper.map(distributorAddressDto, DistributorAddress.class);
		
		return distributorAddress;
	}

}
