package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.CustomerSubContacts;

@Repository
public interface CustomerSubContactsRepo extends JpaRepository<CustomerSubContacts, Integer> {

}
