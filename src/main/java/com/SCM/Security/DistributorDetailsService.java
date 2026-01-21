package com.SCM.Security;

import com.SCM.model.Distributor;
import com.SCM.repository.DistributorRepo;
import com.SCM.serviceimpl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DistributorDetailsService implements UserDetailsService {
	
	   @Autowired
	   DistributorRepo distRepo;
	   
	   @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		   
	        Distributor dist = distRepo.findByPerEmail(username)
	        		.orElseThrow(()->new UsernameNotFoundException("distributor name not found"));
	        
	        return UserDetailsImpl.build1(dist);

	    }

//<<<<<<< HEAD
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    	UserDetailsImpl userImpl = new UserDetailsImpl(null, username, username, username, null);
//    	if (distRepo.findByPerEmail(username).isPresent()) {
//            Distributor dist = distRepo.findByPerEmail(username)
//            		.orElseThrow(()->new UsernameNotFoundException("distributor name not found"));
//            return UserDetailsImpl.build1(dist);
//		}
//    	return null;
//
//
//    }
//=======
//>>>>>>> 02b29a28367b23a12b620abab68afa29b715dd1c
}
