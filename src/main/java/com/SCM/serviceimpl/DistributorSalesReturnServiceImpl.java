package com.SCM.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.dto.DistributorSalesReturnDto;
import com.SCM.model.DistributorSalesReturn;
import com.SCM.repository.DistributorSalesReturnRepository;
import com.SCM.service.DistributorSalesReturnService;

@Service
public class DistributorSalesReturnServiceImpl implements DistributorSalesReturnService {
	
	
	@Autowired
	private DistributorSalesReturnRepository distributorSalesReturnRepository;
	

	@Override
	public DistributorSalesReturn saveDistributorSalesReturn(DistributorSalesReturnDto distributorSalesReturnDto) {

		DistributorSalesReturn distributorSalesReturn=new DistributorSalesReturn();
		
		distributorSalesReturn.setSalesreturnnumber(distributorSalesReturnDto.getSalesreturnnumber());
		distributorSalesReturn.setSalesreturndate(distributorSalesReturnDto.getSalesreturndate());
		distributorSalesReturn.setOriginalinvoiceno(distributorSalesReturnDto.getOriginalinvoiceno());
		distributorSalesReturn.setOriginalinvoicedate(distributorSalesReturnDto.getOriginalinvoicedate());
		distributorSalesReturn.setTermsofpayment(distributorSalesReturnDto.getTermsofpayment());
		distributorSalesReturn.setOtherreference(distributorSalesReturnDto.getOtherreference());
		distributorSalesReturn.setDispatchdocumentnumber(distributorSalesReturnDto.getDispatchdocumentnumber());
		distributorSalesReturn.setDispatchthrough(distributorSalesReturnDto.getDispatchthrough());
		distributorSalesReturn.setDestination(distributorSalesReturnDto.getDestination());
		distributorSalesReturn.setTermsofdelivery(distributorSalesReturnDto.getTermsofdelivery());
		distributorSalesReturn.setDistributorSalesReturnItems(distributorSalesReturnDto.getDistributorSalesReturnItems());
		
		
		DistributorSalesReturn save = distributorSalesReturnRepository.save(distributorSalesReturn);
		
		
		return save;
	}

	@Override
	public List<DistributorSalesReturn> getAll() {

		return distributorSalesReturnRepository.findAll();
	}

	@Override
	public Optional<DistributorSalesReturn> findByDistrubutorSalesReturnId(long id) {

		Optional<DistributorSalesReturn> optional=distributorSalesReturnRepository.findById(id);
		
		return optional;
	}

	@Override
	public DistributorSalesReturn updateDistributorSalesReturn(DistributorSalesReturnDto distributorSalesReturnDto,
			long id) {

DistributorSalesReturn distributorSalesReturn = distributorSalesReturnRepository.findById(id).get();
		
	 
		distributorSalesReturn.setSalesreturnnumber(distributorSalesReturnDto.getSalesreturnnumber());
		distributorSalesReturn.setSalesreturndate(distributorSalesReturnDto.getSalesreturndate());
		distributorSalesReturn.setOriginalinvoiceno(distributorSalesReturnDto.getOriginalinvoiceno());
		distributorSalesReturn.setOriginalinvoicedate(distributorSalesReturnDto.getOriginalinvoicedate());
		distributorSalesReturn.setTermsofpayment(distributorSalesReturnDto.getTermsofpayment());
		distributorSalesReturn.setOtherreference(distributorSalesReturnDto.getOtherreference());
		distributorSalesReturn.setDispatchdocumentnumber(distributorSalesReturnDto.getDispatchdocumentnumber());
		distributorSalesReturn.setDispatchthrough(distributorSalesReturnDto.getDispatchthrough());
		distributorSalesReturn.setDestination(distributorSalesReturnDto.getDestination());
		distributorSalesReturn.setTermsofdelivery(distributorSalesReturnDto.getTermsofdelivery());
		distributorSalesReturn.setDistributorSalesReturnItems(distributorSalesReturnDto.getDistributorSalesReturnItems());
		
		
		return distributorSalesReturnRepository.save(distributorSalesReturn);
	}

	@Override
	public void deleteDistributorSalesReturn(long id) {

		distributorSalesReturnRepository.deleteById(id);
		
		
	}

}
