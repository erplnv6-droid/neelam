package com.SCM.controller;


import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.CustomProvider.EmailOtpAuthenticationToken;
import com.SCM.CustomProvider.LoginRequest1;
import com.SCM.GstLoginUserDto.EwayBillAuthResponseDto;
import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.dto.EmailDto;
import com.SCM.dto.VerifyEmailAndOtp;
import com.SCM.exception.TokenRefreshException;
import com.SCM.jwt.JwtUtils;
import com.SCM.model.Attendence;
import com.SCM.model.Distributor;
import com.SCM.model.ERole;
import com.SCM.model.MultipleStaff;
import com.SCM.model.RefreshToken;
import com.SCM.model.Retailer;
import com.SCM.model.Role;
import com.SCM.model.Staff;
import com.SCM.model.Supplier;
import com.SCM.model.User;
import com.SCM.model.VerificationCode;
import com.SCM.payload.JwtResponse;
import com.SCM.payload.LoginRequest;
import com.SCM.payload.MessageResponse;
import com.SCM.payload.SignupRequest;
import com.SCM.payload.StaffRequest;
import com.SCM.payload.TokenRefreshRequest;
import com.SCM.payload.TokenRefreshResponse;
import com.SCM.repository.AttendenceRepo;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.RoleRepository;
import com.SCM.repository.StaffRepo;
import com.SCM.repository.SupplierRepository;
import com.SCM.repository.UserRepository;
import com.SCM.repository.VerificationCodeRepository;
import com.SCM.repository.ZoneRepo;
import com.SCM.service.EinvoiceService;
import com.SCM.service.EmailSenderService;
import com.SCM.service.EwayBillService;
import com.SCM.service.GstService;
import com.SCM.service.RefreshTokenService;
import com.SCM.serviceimpl.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	StaffRepo staffRepo;

	@Autowired
	private DistributorRepo distributorRepo;

	@Autowired
	private RetailerRepo retailerRepo;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
	
    @Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	ZoneRepo zoneRepo;

	@Autowired
	private AttendenceRepo attendenceRepo;

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailSenderService senderService;

//    sending otp for verification 

	@Autowired
	private VerificationCodeRepository verificationCodeRepository;

	@Autowired
	private EinvoiceService einvoiceService;

	@Autowired
	private GstService gstService;

	@Autowired
	private EwayBillService ewayBillService;

	@PostMapping("/sendverificationcode/otp")
	public ResponseEntity<?> sendVerificationCode(@RequestBody EmailDto emailDto) {
		System.out.println("otppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");

		if (supplierRepository.existsByEmail(emailDto.getEmail()) || staffRepo.existsByEmail(emailDto.getEmail())
				|| distributorRepo.existsByPerEmail(emailDto.getEmail())
				|| retailerRepo.existsByPerEmail(emailDto.getEmail())) {
			System.out.println("otppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");

			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));

		}

		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		System.out.println(otp + "otppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
		Instant instant = Instant.now();

		if (verificationCodeRepository.findByEmail(emailDto.getEmail()).isPresent()) {
			VerificationCode code = verificationCodeRepository.findByEmail(emailDto.getEmail()).get();

			VerificationCode code2 = code;
			System.out.println("outppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
			code.setTime(instant);
			code.setOtp(otp);
			code.setEmail(emailDto.getEmail());
			senderService.sendEmail(emailDto.getEmail(), "Email Verification: ", "Your 6 digit OTP is " + otp);
			senderService.sendEmail(emailDto.getEmail(), "Email Verification: ", "Your 6 digit OTP is " + otp);
			verificationCodeRepository.save(code2);
			return new ResponseEntity<>("OTP updated successfully", HttpStatus.OK);

		}

		else {

			VerificationCode code = new VerificationCode();
			code.setEmail(emailDto.getEmail());
			code.setOtp(otp);
			code.setTime(instant);
			verificationCodeRepository.save(code);
			senderService.sendEmail(emailDto.getEmail(), "Email Verification: ", "Your 6 digit OTP is " + otp);
			// Store OTP in memory
			return new ResponseEntity<String>("Successfully sent the OTP on email " + emailDto.getEmail(),
					HttpStatus.OK);
		}

	}
//    end sending otp for verification 

	@PostMapping("/verifyEmailWithOtp")
	public ResponseEntity<?> verifiEmail(@RequestBody VerifyEmailAndOtp verifyEmailAndOtp) {

		Optional<VerificationCode> user1 = verificationCodeRepository.findByEmail(verifyEmailAndOtp.getEmail());

		System.out.println(user1 + "++++++++++++++++++= user1 ");

		VerificationCode user = user1.get();
		String email = user.getEmail();
		int otp = user.getOtp();
		long id = user.getId();
		Instant otpTime = user.getTime();

		Instant instant2 = Instant.now();

		Duration duration = Duration.between(otpTime, instant2);
		long diff = duration.getSeconds();

		if (diff > 300) {
			verificationCodeRepository.deleteById(id);
			return new ResponseEntity<String>("time limit exceed !!! please generate new otp .", HttpStatus.OK);
		}

		if (verifyEmailAndOtp.getEmail().equals(email) && verifyEmailAndOtp.getOtp() == otp) {
			verificationCodeRepository.deleteById(id);
			return new ResponseEntity<String>("verification done ", HttpStatus.OK);

		}

		return new ResponseEntity<String>("invalid email or otp", HttpStatus.OK);

	}

	public long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long uid = userDetails.getId();

		return uid;
	}

	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return username;
	}

	public String getRolename() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		String role = list.get(0);
		return role;
	}

	// ---------------------STAFF CREATE----------------------

	@PostMapping("/user/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody StaffRequest staffRequest) {

		if (supplierRepository.existsByEmail(staffRequest.getEmail())
				|| staffRepo.existsByEmail(staffRequest.getEmail())
				|| distributorRepo.existsByPerEmail(staffRequest.getEmail())
				|| retailerRepo.existsByPerEmail(staffRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		
//		code for multiple  staff
		
	    List<MultipleStaff> MultipleStaffList = staffRequest.getMultipleStaffDtos().stream()
	        .flatMap(staffDto -> {
	            	        	
	        	List<MultipleStaff> staffList = new ArrayList<>();

	            // Find the maximum length of the lists
	            int maxLength = Math.max(staffDto.getRsmid().size(), 
                                Math.max(staffDto.getAsmid().size(), 
                                Math.max(staffDto.getAseid().size(),
                                         staffDto.getNsmid().size())));
	                       

	            for (int i = 0; i < maxLength; i++) {
	                
	            	MultipleStaff MultipleStaff = new MultipleStaff();
	            	               
	                // Set rsmid if available
	                if (i < staffDto.getRsmid().size()) {
	                	MultipleStaff.setRsmid(staffDto.getRsmid().get(i));
	                    
	                }

	                // Set asmid if available
	                if (i < staffDto.getAsmid().size()) {
	                	MultipleStaff.setAsmid(staffDto.getAsmid().get(i));
	                }

	                // Set aseid if available
	                if (i < staffDto.getAseid().size()) {
	                	MultipleStaff.setAseid(staffDto.getAseid().get(i));
	                }
	                
	                // set nsmid if available
	                if (i < staffDto.getNsmid().size()) {
	                	MultipleStaff.setNsmid(staffDto.getNsmid().get(i));
	                }

	                staffList.add(MultipleStaff);
	            }

	            return staffList.stream();
	        }).collect(Collectors.toList());

//	    ==============================================================

		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		Set<Role> roles = new HashSet<>();

		Role userRole = roleRepository.findById(staffRequest.getRoleId().intValue()).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);

		Staff staff = new Staff();
		staff.setStaffName(staffRequest.getStaffName());
		staff.setDoj(staffRequest.getDoj());
		staff.setAddress(staffRequest.getAddress());
		staff.setMobileNumber(staffRequest.getMobileNumber());
		staff.setEmail(staffRequest.getEmail());
		staff.setGender(staffRequest.getGender());
		staff.setSalary(staffRequest.getSalary());
		staff.setArea(staffRequest.getArea());
		staff.setDateOfBirth(staffRequest.getDateOfBirth());
		staff.setBloodGroup(staffRequest.getBloodGroup());
		staff.setFatherName(staffRequest.getFatherName());
		staff.setSpouseName(staffRequest.getSpouseName());
		staff.setBankDetail(staffRequest.getBankDetail());
		staff.setAccountNumber(staffRequest.getAccountNumber());
		staff.setIfscCode(staffRequest.getIfscCode());
		staff.setBankName(staffRequest.getBankName());
		staff.setBranchName(staffRequest.getBranchName());
		staff.setPanNumber(staffRequest.getPanNumber());
		staff.setAadharNumber(staffRequest.getAadharNumber());
		staff.setLocation(staffRequest.getLocation());
		staff.setSalesexpwithincity(staffRequest.getSalesexpwithincity());
		staff.setSalesexpoutsidecity(staffRequest.getSalesexpoutsidecity());
		staff.setSalesexpdaytrip(staffRequest.getSalesexpdaytrip());
		
		staff.setDateOfAnniversary(staffRequest.getDateOfAnniversary());
		staff.setPassword(encoder.encode(staffRequest.getPassword()));
		staff.setPasswordDecrypted(staffRequest.getPassword());
		staff.setAseId(staffRequest.getAseId());
		staff.setAsmId(staffRequest.getAsmId());
		staff.setRsmId(staffRequest.getRsmId());
		staff.setNsmId(staffRequest.getNsmId());
		staff.setStateZoneId(staffRequest.getStateZoneId());
		staff.setBranch(staffRequest.getBranch());
		staff.setCreateddate(LocalDate.now());
		staff.setCreatedtime(LocalTime.now());
		staff.setEmailLoginStatus(staffRequest.getEmailLoginStatus());
		staff.setRoles(roles);
		staff.setCreatebyname(uname);
		staff.setCreatedby(uid);
		staff.setRole(role);
		staff.setMultipleStaffe(MultipleStaffList);		
		staff.setZones(staffRequest.getZones());
		staff.setStatezones(staffRequest.getStatezones());
		staff.setStafflocation(staffRequest.getStafflocation());
		Staff newStaff = staffRepo.save(staff);
		

		
		return new ResponseEntity<>(newStaff,HttpStatus.CREATED);
	}

//    ---------------------STAFF CREATE----------------------

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {

		System.out.println(loginRequest);
		System.out.println("supplier login" + loginRequest.getEmail() + " and pass " + loginRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String jwt = jwtUtils.generateJwtToken(authentication);
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles1 = new ArrayList<>();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			roles1.add(authority.getAuthority());
		}
		String rolesString = String.join(",", roles1);
		System.out.println("User's Roles: " + rolesString);

//		System.out.println(jwtCookie.toString());

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

//        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
//				.getPrincipal();
//		Long loggeduser = userDetailsImpl.getId();

//		Staff st=staffRepo.findById(loggeduser).get();

//		Attendence attendence=new Attendence();
//		Date date = Date.valueOf(LocalDate.now());
//		LocalTime now = LocalTime.now();
//		attendence.setDayin(date);
//		attendence.setDayintime(now);
//		attendence.setStaff(st);
//		attendenceRepo.save(attendence);
        
       GstLoginDto gstLoginDto = einvoiceService.userLogin();
       if(gstLoginDto != null)
       {
    	   GstLoginDto newgstLoginDto=gstService.saveGstLoginUsetr(gstLoginDto);
           
   		  EwayBillAuthResponseDto billAuthResponseDto=ewayBillService.ewayAuthentication();
   		
   		  EwayBillAuthResponseDto billAuthResponseDto2=gstService.saveEwayResponse(billAuthResponseDto);   
       }
       else {

//   		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new JwtResponse(jwt, null,
//   				userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
       }
       Long id = userDetails.getId();
       
       List<Attendence> findbyid = attendenceRepo.findbyid(id);
       

		for(Attendence attendence:findbyid)
		{
		
		LocalTime localTime=LocalTime.now();

		LocalTime localTime2=LocalTime.of(23, 00);
		
		Date dayout = attendence.getDayin();
		
		String checkinLocationLatitude = attendence.getCheckinLocationLatitude();
		
		String checkinLocationLongitude = attendence.getCheckinLocationLongitude();
		
	  LocalDate currentLocalDate = LocalDate.now();
	    Date currentDate = Date.valueOf(currentLocalDate);

		if(localTime.isAfter(localTime2) && attendence.getDayouttime()==null)
		{
			attendence.setDayouttime(localTime2);
	
	        attendence.setDayout(dayout);
	        
	        attendence.setCheckoutLocationLatitude(checkinLocationLatitude);
	        attendence.setCheckoutLocationLongitude(checkinLocationLongitude);
	
	attendenceRepo.save(attendence);
		}
		}
		
       
       
       
       return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new JwtResponse(jwt, null,
   				userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	 
        

	}

//    login with otp
	@PostMapping("/signinotp/otp")
	public ResponseEntity<?> authenticateUser1(@Valid @RequestBody LoginRequest1 loginRequest) {

//System.out.println("otp login me aaya");
		if (staffRepo.existsByEmail(loginRequest.getEmail())) {
			Staff staff = staffRepo.findByEmail(loginRequest.getEmail()).get();
			Instant instant2 = Instant.now();
			Instant instant = staff.getTime();
			Duration duration = Duration.between(instant, instant2);
			long diff = duration.getSeconds();
			if (diff > 300) {
				Staff staff1 = staff;
				staff.setEmailotp(" ");
				staff.setTime(instant.EPOCH);
				staffRepo.save(staff1);
				throw new RuntimeException(" your otp is expired please generate new one ");
			}

			Authentication authentication = authenticationManager
					.authenticate(new EmailOtpAuthenticationToken(loginRequest.getEmail(), loginRequest.getOtp()));
			System.out.println(authentication.getAuthorities());

			if (authentication.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				String jwt = jwtUtils.generateJwtToken(authentication);
				System.out.println("jwt" + jwt);
				ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
				System.out.println("jwtCookie" + jwtCookie);
				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());

				Staff staff1 = staff;
				staff.setEmailotp(" ");
				staff.setTime(instant.EPOCH);
				staffRepo.save(staff1);

				return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new JwtResponse(
						jwt, null, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
			}

		} else if (supplierRepository.findByEmail(loginRequest.getEmail()).isPresent()) {
			System.out.println("aayaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa  supplier me");

			Supplier supplier = supplierRepository.findByEmail(loginRequest.getEmail()).get();
			System.out.println(supplier.getEmail() + "detailsssssssssssssssssss");
			Instant instant2 = Instant.now();
			Instant instant = supplier.getTime();
			Duration duration = Duration.between(instant, instant2);
			long diff = duration.getSeconds();
			if (diff > 300) {
				Supplier supplier2 = supplier;
				supplier.setEmailotp(" ");
				supplier.setTime(instant.EPOCH);
				supplierRepository.save(supplier2);
				throw new RuntimeException(" your otp is expired please generate new one ");

			}

			Authentication authentication = authenticationManager
					.authenticate(new EmailOtpAuthenticationToken(loginRequest.getEmail(), loginRequest.getOtp()));
			System.out.println(authentication.getAuthorities());

			if (authentication.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				String jwt = jwtUtils.generateJwtToken(authentication);
				ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());

				Supplier supplier2 = supplier;
				supplier.setEmailotp(" ");
				supplier.setTime(instant.EPOCH);
				supplierRepository.save(supplier2);

				return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new JwtResponse(
						jwt, null, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
			}
		} else if (distributorRepo.findByPerEmail(loginRequest.getEmail()).isPresent()) {
			System.out.println("aayaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa  dist me");
			Distributor distributor = distributorRepo.findByPerEmail(loginRequest.getEmail()).get();
			System.out.println(distributor.getPerEmail() + "detailsssssssssssssssssss");
			Instant instant2 = Instant.now();
			Instant instant = distributor.getTime();
			Duration duration = Duration.between(instant, instant2);
			long diff = duration.getSeconds();
			if (diff > 300) {
				Distributor distributor2 = distributor;
				distributor.setEmailotp(" ");
				distributor.setTime(instant.EPOCH);
				distributorRepo.save(distributor2);
				throw new RuntimeException(" your otp is expired please generate new one ");

			}

			Authentication authentication = authenticationManager
					.authenticate(new EmailOtpAuthenticationToken(loginRequest.getEmail(), loginRequest.getOtp()));
			System.out.println(authentication.getAuthorities());

			if (authentication.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				String jwt = jwtUtils.generateJwtToken(authentication);
				ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());

				Distributor distributor2 = distributor;
				distributor.setEmailotp(" ");
				distributor.setTime(instant.EPOCH);
				distributorRepo.save(distributor2);

				return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new JwtResponse(
						jwt, null, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
			}
		}

		else if (retailerRepo.findByPerEmail(loginRequest.getEmail()).isPresent()) {
			System.out.println("aayaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa  supplier me");

			Retailer retailer = retailerRepo.findByPerEmail(loginRequest.getEmail()).get();
			System.out.println(retailer.getEmail1() + "detailsssssssssssssssssss");
			Instant instant2 = Instant.now();
			Instant instant = retailer.getTime();
			Duration duration = Duration.between(instant, instant2);
			long diff = duration.getSeconds();
			if (diff > 300) {
				Retailer retailer2 = retailer;
				retailer.setEmailotp(" ");
				retailer.setTime(instant.EPOCH);
				retailerRepo.save(retailer2);
				throw new RuntimeException(" your otp is expired please generate new one ");

			}

			Authentication authentication = authenticationManager
					.authenticate(new EmailOtpAuthenticationToken(loginRequest.getEmail(), loginRequest.getOtp()));
			System.out.println(authentication.getAuthorities());

			if (authentication.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				String jwt = jwtUtils.generateJwtToken(authentication);
				ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());

				Retailer retailer2 = retailer;
				retailer.setEmailotp(" ");
				retailer.setTime(instant.EPOCH);
				retailerRepo.save(retailer2);

				return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new JwtResponse(
						jwt, null, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
			}
		}
		throw new RuntimeException("your email id is not present ");

	}
//    login with otp

//    sending otp 

	@PutMapping("/otp")
	public ResponseEntity<?> updateUser(@RequestBody EmailDto emailDto) {

		if (staffRepo.existsByEmail(emailDto.getEmail())) {
			Staff user = staffRepo.findByEmail(emailDto.getEmail()).get();
			if (user.getEmailLoginStatus().equals("Disable")) {
				throw new RuntimeException("you are not enable for otp login  ");

			}
			Staff user1 = user;
			Random random = new Random();
			int otp = 100000 + random.nextInt(900000);
			System.out.println(otp + "otppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
			Instant instant = Instant.now();
			user.setTime(instant);
			user.setEmailotp(passwordEncoder.encode(String.valueOf(otp)));

			senderService.sendEmail(emailDto.getEmail(), "Email Verification: ", "Your 6 digit OTP is " + otp);
			staffRepo.save(user1);
			return new ResponseEntity<String>("succesfully send the otp on email " + emailDto.getEmail(),
					HttpStatus.OK);
		}

		else if (supplierRepository.findByEmail(emailDto.getEmail()).isPresent()) {
			Supplier supplier = supplierRepository.findByEmail(emailDto.getEmail()).get();
			if (supplier.getEmailLoginStatus().equals("Disable")) {
				throw new RuntimeException("you are not enable for otp login  ");
			}
			Supplier supplier1 = supplier;
			Random random = new Random();
			int otp = 100000 + random.nextInt(900000);
			System.out.println(otp + "otppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
			Instant instant = Instant.now();
			supplier.setTime(instant);
			supplier.setEmailotp(passwordEncoder.encode(String.valueOf(otp)));

			senderService.sendEmail(emailDto.getEmail(), "Email Verification: ", "Your 6 digit OTP is " + otp);
			supplierRepository.save(supplier1);
			return new ResponseEntity<String>("succesfully send the otp on email " + emailDto.getEmail(),
					HttpStatus.OK);
		}

		else if (distributorRepo.findByPerEmail(emailDto.getEmail()).isPresent()) {
			Distributor distributor = distributorRepo.findByPerEmail(emailDto.getEmail()).get();
			if (distributor.getEmailLoginStatus().equals("Disable")) {
				throw new RuntimeException("you are not enable for otp login  ");
			}

			Distributor distributor1 = distributor;

			Random random = new Random();
			int otp = 100000 + random.nextInt(900000);
			System.out.println(otp + "otppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
			Instant instant = Instant.now();
			distributor.setTime(instant);
			distributor.setEmailotp(passwordEncoder.encode(String.valueOf(otp)));

			senderService.sendEmail(emailDto.getEmail(), "Email Verification: ", "Your 6 digit OTP is " + otp);
			distributorRepo.save(distributor1);
			return new ResponseEntity<String>("succesfully send the otp on email " + emailDto.getEmail(),
					HttpStatus.OK);
		}

		else if (retailerRepo.findByPerEmail(emailDto.getEmail()).isPresent()) {
			Retailer retailer = retailerRepo.findByPerEmail(emailDto.getEmail()).get();
			if (retailer.getEmailLoginStatus().equals("Disable")) {
				throw new RuntimeException("you are not enable for otp login  ");
			}
			Retailer retailer1 = retailer;

			Random random = new Random();
			int otp = 100000 + random.nextInt(900000);
			System.out.println(otp + "otppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
			Instant instant = Instant.now();
			retailer.setTime(instant);
			retailer.setEmailotp(passwordEncoder.encode(String.valueOf(otp)));

			senderService.sendEmail(emailDto.getEmail(), "Email Verification: ", "Your 6 digit OTP is " + otp);
			retailerRepo.save(retailer1);
			return new ResponseEntity<String>("succesfully send the otp on email " + emailDto.getEmail(),
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("Your email id not available ", HttpStatus.OK);

	}

//    sending otp end
	@PostMapping("/signup")
	public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getStaffName());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("You've been signed out!"));
	}
}
