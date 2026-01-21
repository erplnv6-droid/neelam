package com.SCM.service;

import org.springframework.stereotype.Service;
import com.SCM.model.RefreshToken;

@Service
public interface RefreshService {

	public RefreshToken saveRefreshTokenRetailer(RefreshToken refreshToken); 
}
