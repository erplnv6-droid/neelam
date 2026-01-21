package com.SCM.service;

import java.util.List;
import com.SCM.dto.CustomerSubContactsDto;
import com.SCM.model.CustomerSubContacts;

public interface CustomerSubContactsService {

	public CustomerSubContacts savesubContacts(CustomerSubContactsDto customerSubContactsDto);

	public List<CustomerSubContacts> getAllSubContacts();

	public CustomerSubContacts getSubContactsById(int id);

	void deleteSubContacts(int id);

	public CustomerSubContacts updateSubContacts(CustomerSubContacts customerSubContacts, int id);
}
