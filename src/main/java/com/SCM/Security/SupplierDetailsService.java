package com.SCM.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SCM.model.Supplier;
import com.SCM.repository.SupplierRepository;
import com.SCM.serviceimpl.UserDetailsImpl;

@Service
public class SupplierDetailsService implements UserDetailsService {

	@Autowired
	SupplierRepository supplierRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Supplier supplier = supplierRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException(username + "email not found"));
		return UserDetailsImpl.build3(supplier);

		
	}
}
