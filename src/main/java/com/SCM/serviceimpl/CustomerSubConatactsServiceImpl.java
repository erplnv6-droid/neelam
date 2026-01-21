package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SCM.dto.CustomerSubContactsDto;
import com.SCM.model.Customer;
import com.SCM.model.CustomerSubContacts;
import com.SCM.repository.CustomerRepository;
import com.SCM.repository.CustomerSubContactsRepo;
import com.SCM.service.CustomerSubContactsService;

@Service
public class CustomerSubConatactsServiceImpl implements CustomerSubContactsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerSubContactsRepo customerSubContactsRepo;

	@Override
	public CustomerSubContacts savesubContacts(CustomerSubContactsDto customerSubContactsDto) {

		Optional<Customer> customer = customerRepository.findById(customerSubContactsDto.getCustomer().getId());
		Customer c = customer.get();

		CustomerSubContacts customerSubContacts = new CustomerSubContacts();
		customerSubContacts.setContactname(customerSubContactsDto.getContactname());
		customerSubContacts.setEmail(customerSubContactsDto.getEmail());
		customerSubContacts.setPhoneno(customerSubContactsDto.getPhoneno());
		
		customerSubContacts.setCreateddate(LocalDate.now());
		customerSubContacts.setCreatedtime(LocalTime.now());

		CustomerSubContacts csc = customerSubContactsRepo.save(customerSubContacts);
		
		c.getCustomerSubContacts().add(customerSubContacts);
		customerRepository.save(c);

		return csc;
	}

	
	
	@Override
	public List<CustomerSubContacts> getAllSubContacts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerSubContacts getSubContactsById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSubContacts(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public CustomerSubContacts updateSubContacts(CustomerSubContacts customerSubContacts, int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
