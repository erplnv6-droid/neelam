package com.SCM.CustomProvider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.SCM.Security.DistributorDetailsService;
import com.SCM.Security.RetailerDetailsService;
import com.SCM.Security.SupplierDetailsService;
import com.SCM.model.Distributor;
import com.SCM.model.Retailer;
import com.SCM.model.Staff;
import com.SCM.model.Supplier;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.StaffRepo;
import com.SCM.repository.SupplierRepository;
import com.SCM.serviceimpl.UserDetailsImpl;
import com.SCM.serviceimpl.UserDetailsServiceImpl;


@Component
public class CustomAuthenticationProvider1 implements AuthenticationProvider{

	@Autowired
    private StaffRepo staffRepo;
	@Autowired
	private SupplierRepository supplierRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private SupplierDetailsService supplierDetailsService;
    
    @Autowired
    private DistributorRepo distributorRepo;
    @Autowired
    private DistributorDetailsService distributorDetailsService;
    
    @Autowired
    private RetailerRepo retailerRepo;
    
    @Autowired
    private RetailerDetailsService retailerDetailsService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String otp = authentication.getCredentials().toString();

        if (staffRepo.existsByEmail(email)) {
            Staff staff = staffRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
            if (passwordEncoder.matches(otp, staff.getEmailotp())) {
                // Retrieve UserDetailsImpl object using email
                UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);

                // Collect user authorities (roles) here if available
                List<GrantedAuthority> authorities = new ArrayList<>();
                // Add user roles to authorities list if available
                // Example: authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                // Set authorities to UserDetailsImpl object
                userDetails.setAuthorities(authorities);

                return new EmailOtpAuthenticationToken(userDetails, otp, authorities);
            } else {
                throw new BadCredentialsException("Invalid OTP!");
            }
        }
        
        else if (supplierRepository.findByEmail(email).isPresent()) {
            Supplier supplier = supplierRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
            if (passwordEncoder.matches(otp, supplier.getEmailotp())) {
                // Retrieve UserDetailsImpl object using email
                UserDetailsImpl userDetails = (UserDetailsImpl) supplierDetailsService.loadUserByUsername(email);

                // Collect user authorities (roles) here if available
                List<GrantedAuthority> authorities = new ArrayList<>();
                // Add user roles to authorities list if available
                // Example: authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                // Set authorities to UserDetailsImpl object
                userDetails.setAuthorities(authorities);

                return new EmailOtpAuthenticationToken(userDetails, otp, authorities);
            } else {
                throw new BadCredentialsException("Invalid OTP");
            }
        }
        
        else if (distributorRepo.findByPerEmail(email).isPresent()) {
            Distributor dist = distributorRepo.findByPerEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
            if (passwordEncoder.matches(otp, dist.getEmailotp())) {
                // Retrieve UserDetailsImpl object using email
                UserDetailsImpl userDetails = (UserDetailsImpl) distributorDetailsService.loadUserByUsername(email);

                // Collect user authorities (roles) here if available
                List<GrantedAuthority> authorities = new ArrayList<>();
                // Add user roles to authorities list if available
                // Example: authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                // Set authorities to UserDetailsImpl object
                userDetails.setAuthorities(authorities);

                return new EmailOtpAuthenticationToken(userDetails, otp, authorities);
            } else {
                throw new BadCredentialsException("Invalid OTP");
            }
        }
        
        else if (retailerRepo.findByPerEmail(email).isPresent()) {
            Retailer retailer = retailerRepo.findByPerEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
            if (passwordEncoder.matches(otp, retailer.getEmailotp())) {
                // Retrieve UserDetailsImpl object using email
                UserDetailsImpl userDetails = (UserDetailsImpl) retailerDetailsService.loadUserByUsername(email);

                // Collect user authorities (roles) here if available
                List<GrantedAuthority> authorities = new ArrayList<>();
                // Add user roles to authorities list if available
                // Example: authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                // Set authorities to UserDetailsImpl object
                userDetails.setAuthorities(authorities);

                return new EmailOtpAuthenticationToken(userDetails, otp, authorities);
            } else {
                throw new BadCredentialsException("Invalid OTP");
            }
        }
        
        
        
        
        
        
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailOtpAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
