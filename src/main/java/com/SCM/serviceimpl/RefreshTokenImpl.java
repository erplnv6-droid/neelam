package com.SCM.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SCM.model.RefreshToken;
import com.SCM.repository.RefreshTokenRepository;
import com.SCM.service.RefreshService;

@Service
public class RefreshTokenImpl implements RefreshService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	
	@Override
	public RefreshToken saveRefreshTokenRetailer(RefreshToken refreshToken) {
		
		RefreshToken rf = new RefreshToken();
		rf.setRetailer(refreshToken.getRetailer());
		rf.setExpiryDate(refreshToken.getExpiryDate());
		rf.setToken(refreshToken.getToken());
		
		return refreshTokenRepository.save(rf);
	}

}
