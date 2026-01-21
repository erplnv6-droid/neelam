package com.SCM.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SCM.dto.DistributorOpeningStockDto;
import com.SCM.model.DistributorOpeningStock;
@Component
public class DistributorOpeningStockMapper {

	@Autowired
	
	private ModelMapper mapper;
	
	
	public DistributorOpeningStock mapToDistributorOpeningStock(DistributorOpeningStockDto distributorOpeningStockDto)
	{
		DistributorOpeningStock distributorOpeningStock=mapper.map(distributorOpeningStockDto, DistributorOpeningStock.class);
		
		return distributorOpeningStock;
	}
	
	public DistributorOpeningStockDto mapToDistributorOpeningStockDto(DistributorOpeningStock distributorOpeningStock)
	{
		DistributorOpeningStockDto distributorOpeningStockDto=mapper.map(distributorOpeningStock, DistributorOpeningStockDto.class);
		
		return distributorOpeningStockDto;
	}
}
