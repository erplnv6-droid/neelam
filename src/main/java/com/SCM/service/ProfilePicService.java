package com.SCM.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.ProfilePictureDto;
import com.SCM.model.ProfilePicture;

public interface ProfilePicService {

	public String upload(int staffid,int retailerid,int distributorid,int supplierid,MultipartFile file) throws IOException;
	
	public List<ProfilePicture> getProfileByStaffId(int staffid);
	
	public List<ProfilePicture> getProfileByRetailerId(int retailerid);
	
	public List<ProfilePicture> getProfileByDistributorId(int distributorid);
	
	public List<ProfilePicture> getProfileBySupplierId(int supplierid);
	
	public String update(int id,ProfilePictureDto profilePictureDto,int staffid,int retailerid,int distributorid,int supplierid,MultipartFile file) throws IOException;
	
}
