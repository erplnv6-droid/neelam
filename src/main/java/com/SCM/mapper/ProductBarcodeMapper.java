package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SCM.dto.ProductBarcodeDto;
import com.SCM.model.ProductBarcode;

@Component
public class ProductBarcodeMapper {

	@Autowired
	private Mapper mapper;
	
	public ProductBarcode toEntity(ProductBarcodeDto dto) {
		return mapper.getModelMapper().map(dto, ProductBarcode.class);
	}
	
	public ProductBarcodeDto toDto(ProductBarcode barcode) {
		return mapper.getModelMapper().map(barcode, ProductBarcodeDto.class);
	}
	
}
