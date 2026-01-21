package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SCM.IndexDto.IndexOpeningStock;
import com.SCM.IndexDto.IndexOpeningStockDto;
import com.SCM.IndexDto.IndexOpeningStockEntity;

@Component
public class IndexOpeningStockMapper {

	@Autowired
	private Mapper mapper;
	
	public IndexOpeningStock toDto(IndexOpeningStockEntity indexOpeningStockEntity) {
		return mapper.getModelMapper().map(indexOpeningStockEntity, IndexOpeningStock.class);
	}
	
	public IndexOpeningStockEntity toEntity(IndexOpeningStock indexOpeningStockDto) {
		return mapper.getModelMapper().map(indexOpeningStockDto, IndexOpeningStockEntity.class);
	}
	
}
