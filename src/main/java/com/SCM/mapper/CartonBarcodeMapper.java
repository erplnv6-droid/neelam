package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.SCM.dto.CartonBarcodeDto;
import com.SCM.model.CartonBarcode;

@Configuration
public class CartonBarcodeMapper {

	@Autowired
	private Mapper mapper;
	
	public CartonBarcode toEntity(CartonBarcodeDto dto) {
		return mapper.getModelMapper().map(dto, CartonBarcode.class);
	}
	
	public CartonBarcodeDto toDto(CartonBarcode barcode) {
		return mapper.getModelMapper().map(barcode, CartonBarcodeDto.class);
	}
}
