package com.SCM.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.SCM.dto.SalesAgentExpenseClaimDto;
import com.SCM.model.SalesAgentExpenseClaim;

@Configuration
public class SalesAgentExpenseClaimMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public SalesAgentExpenseClaim toEntity(SalesAgentExpenseClaimDto agentExpenseClaimDto) {
		SalesAgentExpenseClaim map = modelMapper.map(agentExpenseClaimDto, SalesAgentExpenseClaim.class);
		return map;
	} 
	
	public SalesAgentExpenseClaimDto toDto(SalesAgentExpenseClaim agentExpenseClaim) {
		SalesAgentExpenseClaimDto map = modelMapper.map(agentExpenseClaim, SalesAgentExpenseClaimDto.class);
		return map;
	}
}
