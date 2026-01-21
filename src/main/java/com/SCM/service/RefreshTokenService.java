package com.SCM.service;

import com.SCM.exception.TokenRefreshException;
import com.SCM.model.Distributor;
import com.SCM.model.RefreshToken;
import com.SCM.model.Retailer;
import com.SCM.model.Staff;
import com.SCM.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private DistributorRepo distributorRepo;

    @Autowired
    private RetailerRepo retailerRepo;
    
    @Autowired
    private RefreshService refreshService;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        Optional<Staff> staff = staffRepo.findById(userId);
        Optional<Retailer> retailer = retailerRepo.findById(userId.intValue());
        Optional<Distributor> distributor = distributorRepo.findById(userId.intValue());

     //   refreshToken.setUser(retailerRepo.findById(userId.intValue()).get());
        if(staff.isPresent()) {
            refreshToken.setUser(staffRepo.findById(userId).get());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());

            refreshToken = refreshTokenRepository.save(refreshToken);
            System.out.println(refreshToken);
            return refreshToken;
        }
       else if(distributor.isPresent()){
            refreshToken.setUser(distributorRepo.findById(userId.intValue()).get());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());

            refreshToken = refreshTokenRepository.save(refreshToken);
            System.out.println(refreshToken);
            return refreshToken;
            }
        else {
            refreshToken.setUser(retailerRepo.findById(userId.intValue()).get());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
            System.out.println(refreshToken+ " refresh Token");
            refreshToken = refreshService.saveRefreshTokenRetailer(refreshToken);
            return refreshToken;
//            refreshToken.setUser(staffRepo.findById(userId).get());
        }


//        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
//        refreshToken.setToken(UUID.randomUUID().toString());
//
//        refreshToken = refreshTokenRepository.save(refreshToken);
//        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

}
