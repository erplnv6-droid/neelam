package com.SCM.service;

import java.util.List;
import com.SCM.model.SupplierAddress;

public interface SupplierAddressService {

	public List<SupplierAddress> findAllAddressBySupplierId(long id);
	
	public String deleteSupplierAddress(Long id);
}
