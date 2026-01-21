package com.SCM.Security;

import com.SCM.model.Retailer;
import com.SCM.repository.RetailerRepo;
import com.SCM.serviceimpl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RetailerDetailsService implements UserDetailsService {

    @Autowired
    RetailerRepo retailerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	UserDetailsImpl userImpl = new UserDetailsImpl(null, username, username, username, null);
    	
        Retailer ret = retailerRepo.getRetailerByPerEmail(username).orElseThrow(()->new UsernameNotFoundException("retailer name not found"));
            
        return UserDetailsImpl.build2(ret);
		
    }

}
