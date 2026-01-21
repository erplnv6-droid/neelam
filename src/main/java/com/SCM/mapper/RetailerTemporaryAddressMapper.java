package com.SCM.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SCM.dto.RetailerTempDto;
import com.SCM.dto.RetailerTemporaryAddressDto;
import com.SCM.model.RetailerTemporaryAddress;

@Component
public class RetailerTemporaryAddressMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public RetailerTemporaryAddress mapToAddress(RetailerTemporaryAddressDto retailerTempDto)
	{
		RetailerTemporaryAddress temporaryAddress = modelMapper.map(retailerTempDto, RetailerTemporaryAddress.class);
		
		return temporaryAddress;
	}
	
	
	public RetailerTemporaryAddressDto mapToDto(RetailerTemporaryAddress retailerTemporaryAddress)
	{
		RetailerTemporaryAddressDto retailerTempDto = modelMapper.map(retailerTemporaryAddress, RetailerTemporaryAddressDto.class);
		
		return retailerTempDto;
	}

}
