package com.SCM.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.serviceimpl.UserDetailsImpl;

@Service
public class GetCurrentUserRoleName {

	
	public long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
			String username = userDetails.getUsername();
			Long uid = userDetails.getId();

			return uid;
	}
	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return username;
	}

	public String getRolename() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		String role=list.get(0);
		return role;
	}
}
