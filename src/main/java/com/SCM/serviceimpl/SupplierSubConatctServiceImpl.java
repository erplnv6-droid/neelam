package com.SCM.serviceimpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SCM.dto.SupplierSubContactsDto;
import com.SCM.model.Supplier;
import com.SCM.model.SupplierSubContacts;
import com.SCM.repository.SupplierRepository;
import com.SCM.repository.SupplierSubContactRepo;
import com.SCM.service.SupplierSubContactsService;


@Service
public class SupplierSubConatctServiceImpl implements SupplierSubContactsService {

	@Autowired
	private SupplierSubContactRepo subContactRepo;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Override
	public SupplierSubContacts saveSuppliersubContacts(SupplierSubContactsDto supplierSubContactsDto) {
		
		Optional<Supplier> supplier = supplierRepository.findById(supplierSubContactsDto.getSupplier().getId());
		Supplier s = supplier.get();
		
		SupplierSubContacts supplierSubContacts = new SupplierSubContacts();
		supplierSubContacts.setContactname(supplierSubContactsDto.getContactname());
		supplierSubContacts.setEmail(supplierSubContactsDto.getEmail());
		supplierSubContacts.setPhoneno(supplierSubContactsDto.getPhoneno());
		
		SupplierSubContacts ssc = subContactRepo.save(supplierSubContacts);
		s.getSupplierSubContacts().add(supplierSubContacts);
		supplierRepository.save(s);

		return ssc;
	}

	@Override
	public List<SupplierSubContacts> getAllSupplierSubContacts() {
		
		List<SupplierSubContacts> ssc = subContactRepo.findAll();
		
		return ssc;
	}

	@Override
	public SupplierSubContacts getSupplierSubContactsById(int id) {
	
		SupplierSubContacts ssc = subContactRepo.findById(id).get();
		
		return ssc;
	}

	@Override
	public void deleteSupplierSubContacts(int id) {
		
		subContactRepo.deleteById(id);
		
	}

	@Override
	public SupplierSubContacts updateSupplierSubContacts(SupplierSubContacts SupplierSubContacts, int id) {
		
		SupplierSubContacts ssc = subContactRepo.findById(id).get();

		ssc.setContactname(SupplierSubContacts.getContactname());
		ssc.setEmail(SupplierSubContacts.getEmail());
		ssc.setPhoneno(SupplierSubContacts.getPhoneno());

		return subContactRepo.save(ssc);
	}

	
}
 