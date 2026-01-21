package com.SCM.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.model.SupplierAddress;
import com.SCM.repository.SupplierAddressRepo;
import com.SCM.service.SupplierAddressService;

@Service
public class SupplierAddressServiceImpl implements SupplierAddressService {

	
	@Autowired
	private SupplierAddressRepo supplierAddressRepo;
	
	@Override
	public List<SupplierAddress> findAllAddressBySupplierId(long id) {
		
		List<SupplierAddress> allAddressBySupplierId = supplierAddressRepo.findAllAddressBySupplierId(id);
		
		return allAddressBySupplierId;
	}

	@Override
	public String deleteSupplierAddress(Long id) {
		supplierAddressRepo.deleteBySupplierId(id);
		return "";
	}

}
