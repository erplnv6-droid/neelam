package com.SCM.config;

import com.SCM.serviceimpl.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserId {
	
    private Long id;

    private Collection<GrantedAuthority> authorities;

    
    public Long getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        id = userDetails.getId();
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Collection<GrantedAuthority> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
        return authorities;
    }

    
    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    
    private Long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }


}
