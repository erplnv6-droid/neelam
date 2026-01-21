package com.SCM.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.GstLoginUserDto.EwayBillAuthResponseDto;
import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.GstModel.EwayBillData;
import com.SCM.GstModel.GstUserLogin;
import com.SCM.GstRepository.EwayBillAuthRepository;
import com.SCM.GstRepository.GstRepository;
import com.SCM.service.GstLoginService;



@Service
public class GstUserLoginServiceImpl implements GstLoginService{
	
	@Autowired
	private GstRepository gstRepository;
	
	@Autowired
	private EwayBillAuthRepository authRepository;
	
	@Autowired
	
	private ModelMapper mapper;
	
	@Override
	public GstLoginDto saveToken(GstLoginDto gstUser) {
		GstUserLogin gstUserLogin = gstRepository.save(this.gstUserLoginDtoToGstUserLogin(gstUser));
		return this.gstUserLoginToGstUserLoginDto(gstUserLogin);
	}

	@Override
	public GstLoginDto getGstUserLoginByGstin(String gstin) {
		// TODO Auto-generated method stub
		GstUserLogin gstUserLogin=gstRepository.findByGstin(gstin);
		 return gstUserLogin == null ? null : gstUserLoginToGstUserLoginDto(gstUserLogin);
	}
	
	
	@Override
	public EwayBillAuthResponseDto getEwayBillByGstin(String auth_token) {
		// TODO Auto-generated method stub
		EwayBillData ewayBillData=authRepository.findByAuthToken(auth_token);
		return ewayBillData == null ? null : ewayBillToEwayBillDto(ewayBillData);
	}
	
	

	
	public GstUserLogin gstUserLoginDtoToGstUserLogin(GstLoginDto gstDto) {
		return mapper.map(gstDto, GstUserLogin.class);
	}
	public GstLoginDto gstUserLoginToGstUserLoginDto(GstUserLogin gstUser) {
		return mapper.map(gstUser, GstLoginDto.class);
	}
	
	public EwayBillData ewayBillDtoToEwayBill(EwayBillAuthResponseDto authResponseDto)
	{
		return mapper.map(authResponseDto, EwayBillData.class);
	}
	
	public EwayBillAuthResponseDto ewayBillToEwayBillDto(EwayBillData ewayBillData)
	{
		return mapper.map(ewayBillData, EwayBillAuthResponseDto.class);
	}
	
	@Override
	public void deleteGstUserLoginByGstin(String gstin) {
		gstRepository.deleteByGstin(gstin);
		
	}




}
