package com.SCM.service;

import java.util.List;

import com.SCM.dto.SupplierSubContactsDto;
import com.SCM.model.SupplierSubContacts;


public interface SupplierSubContactsService {

	public SupplierSubContacts saveSuppliersubContacts(SupplierSubContactsDto supplierSubContactsDto);

	public List<SupplierSubContacts> getAllSupplierSubContacts();

	public SupplierSubContacts getSupplierSubContactsById(int id);

	void deleteSupplierSubContacts(int id);

	public SupplierSubContacts updateSupplierSubContacts(SupplierSubContacts SupplierSubContacts, int id);
}
