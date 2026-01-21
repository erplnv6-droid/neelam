package com.SCM.jwt;

import com.SCM.Security.DistributorDetailsService;
import com.SCM.Security.RetailerDetailsService;
import com.SCM.Security.SupplierDetailsService;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.StaffRepo;
import com.SCM.serviceimpl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private DistributorDetailsService distributorService;

	@Autowired
	private RetailerDetailsService retailerDetailsService;

	@Autowired
	private SupplierDetailsService supplierDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String email = jwtUtils.getUserNameFromJwtToken(jwt);
				try {
					System.out.println("Staff exce");
					UserDetails userDetails = userDetailsService.loadUserByUsername(email);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					
				} 
				catch (UsernameNotFoundException e) 
				{
					System.out.println("Distri exce");
					UserDetails userDetails = distributorService.loadUserByUsername(email);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				catch (Exception e) 
				{
					System.out.println("supplier exce");
					UserDetails userDetails = supplierDetailsService.loadUserByUsername(email);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
		
				finally {
					System.out.println("Retailer exce");
					
					if (retailerDetailsService.loadUserByUsername(email) != null) {
						UserDetails userDetails = retailerDetailsService.loadUserByUsername(email);
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				

			}
			}}
			catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}
		
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String email = jwtUtils.getUserNameFromJwtToken(jwt);
				try {
					System.out.println("Staff exce");
					UserDetails userDetails = userDetailsService.loadUserByUsername(email);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					
				} 
				catch (UsernameNotFoundException e) 
				{
					
					
					System.out.println("supplier exce");
					UserDetails userDetails = supplierDetailsService.loadUserByUsername(email);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
				}
				catch (Exception e) 
				{
					System.out.println("Distri exce");
					UserDetails userDetails = distributorService.loadUserByUsername(email);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
		
				finally {
					System.out.println("Retailer exce");
					
					if (retailerDetailsService.loadUserByUsername(email) != null) {
						UserDetails userDetails = retailerDetailsService.loadUserByUsername(email);
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				

			}
			}}
			catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		String jwt = jwtUtils.getJwtFromCookies(request);

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
}
