package com.SCM.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SCM.model.RetailerAddress;

@Component
public class RetailerAddressMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public RetailerAddressDto mapToDto(RetailerAddress retailerAddress)
	{
		RetailerAddressDto retailerAddressDto = modelMapper.map(retailerAddress, RetailerAddressDto.class);
		
		return retailerAddressDto;
	}
	
	
	public RetailerAddress mapToRetailerAddress(RetailerAddressDto retailerAddressDto)
	{
		RetailerAddress retailerAddress = modelMapper.map(retailerAddressDto, RetailerAddress.class);
		
		return retailerAddress;
	}

}
