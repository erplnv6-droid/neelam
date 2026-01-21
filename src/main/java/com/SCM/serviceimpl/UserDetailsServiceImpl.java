package com.SCM.serviceimpl;

import com.SCM.model.Staff;
import com.SCM.repository.StaffRepo;
import com.SCM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    StaffRepo staffRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

       Optional<Staff>  staff = staffRepo.findByEmail(email);

        if(staff.isPresent())
        	{
        	   Staff s = staff.get();
        	   return   UserDetailsImpl.build(s); 
        	}else {
        		System.out.println("Exception");
        	}
		throw new UsernameNotFoundException("Username not Found");
        
                 // .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username::: " + email));
   
    }


}
