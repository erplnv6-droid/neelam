package com.SCM.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.ProfilePictureDto;
import com.SCM.model.ProfilePicture;
import com.SCM.repository.ProfilePictureRepo;
import com.SCM.service.ProfilePicService;

@Service
public class ProfilePicServiceImpl implements ProfilePicService {

	@Autowired
	private ProfilePictureRepo profilePictureRepo;

	@Value("${FILE_PATH}")
	private String FILE_PATH;

	private final Path fileStorageLocation;

	public ProfilePicServiceImpl(Environment env) throws IOException {
		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir10", "/profilepic/")).toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation);
	}
	
	private static final long MAX_FILE_SIZE = 3 * 1024 * 1024;
	
//	@Override
//	public String upload(int staffid,int retailerid,int distributorid,MultipartFile file) throws IOException {
//			
//		ProfilePicture picture = new ProfilePicture();		
//		ProfilePicture save = profilePictureRepo.save(picture);
//		
//		String profilepicpath =  "profile_" + staffid +  file.getOriginalFilename();
//		
//		
//		
//		if(file.isEmpty())
//		{
//			picture.setProfilepicname(null);
//			picture.setProfilepiclocation(null);
//		}			
//		if(!file.isEmpty() ) {
//			
//			Files.copy(file.getInputStream(), fileStorageLocation.resolve(profilepicpath),StandardCopyOption.REPLACE_EXISTING);
//			picture.setProfilepicname(file.getOriginalFilename());
//			picture.setStaffId(staffid);
//			picture.setRetailerId(retailerid);
//			picture.setDistributorId(distributorid);
//			picture.setProfilepiclocation("profilepic/" + profilepicpath);
//		
//			profilePictureRepo.save(picture);
//		}
//		
//		return "upload";
//	}
	
//	@Override
//	public String upload(int staffid, int retailerid, int distributorid, MultipartFile file) throws IOException {
//
//	    if (file.isEmpty()) {
//	        return "No file uploaded"; 
//	    }
//
//	    String profilePicPath = "profile_" + staffid + "_" + file.getOriginalFilename();
//	    
//	    ProfilePicture picture = new ProfilePicture();
//	    picture.setProfilepicname(file.getOriginalFilename());
//	    picture.setStaffId(staffid);
//	    picture.setRetailerId(retailerid);
//	    picture.setDistributorId(distributorid);  
//	    picture.setProfilepiclocation("profilepic/" + profilePicPath);
//	    
//	    ProfilePicture existingPicture = profilePictureRepo.findByStaffId(staffid);
//
//	    if (existingPicture != null) {
//
//	    	Path oldFilePath = fileStorageLocation.resolve(existingPicture.getProfilepiclocation());
//
//	        if (Files.exists(oldFilePath)) {
//	        	
//	            try {
//	            	
//	                Files.delete(oldFilePath);
//	                
//	            } catch (IOException e) {
//	               
//	                System.err.println("Error deleting old file: " + oldFilePath.toString());
//	                e.printStackTrace();
//	                return "Error deleting old file";
//	            }
//	        } else {
//	            System.err.println("Old file not found: " + oldFilePath.toString());
//	        }
//
//	        profilePictureRepo.delete(existingPicture);
//	    }
//
//	    Path newFilePath = fileStorageLocation.resolve(profilePicPath);
//	    Files.copy(file.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
//
//	    profilePictureRepo.save(picture);
//
//	    return "Upload successful"; 
//	}
	
	
	
	@Override
	public String upload(int staffid, int retailerid, int distributorid,int supplierid,MultipartFile file) throws IOException {

	    if (file.isEmpty()) {
	    	
	        return "No file uploaded"; 
	    }

	    String profilePicPath = "profile_" + staffid + "_" + retailerid + "_" + distributorid + "_" + supplierid  + "_" + file.getOriginalFilename();
	    
	    ProfilePicture picture = new ProfilePicture();
	    picture.setProfilepicname(file.getOriginalFilename());
	    picture.setStaffId(staffid);
	    picture.setRetailerId(retailerid);
	    picture.setDistributorId(distributorid);  
	    picture.setSupplierId(supplierid);
	    picture.setProfilepiclocation("profilepic/" + profilePicPath);
	    
	    ProfilePicture existingPicture = profilePictureRepo.findByStaffIdAndRetailerIdAndDistributorIdAndSupplierId(staffid, retailerid, distributorid,supplierid);

	    if(file.getSize() > MAX_FILE_SIZE)
	    {
	    	throw new RuntimeException("The file size 3MB execced to limit");
	    }
	    
	    if (existingPicture != null) {

	    	Path oldFilePath = fileStorageLocation.resolve(existingPicture.getProfilepiclocation());

	        if (Files.exists(oldFilePath)) {
	            try {
	                Files.delete(oldFilePath);  
	            } catch (IOException e) {
	                System.err.println("Error deleting old file: " + oldFilePath.toString());
	                e.printStackTrace();
	                return "Error deleting old file";
	            }
	        } else {
	            System.err.println("Old file not found: " + oldFilePath.toString());
	        }

	        profilePictureRepo.delete(existingPicture);
	    }

	    Path newFilePath = fileStorageLocation.resolve(profilePicPath);
	    Files.copy(file.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);

	    profilePictureRepo.save(picture);

	    return "Upload successful"; 
	}



	
	
	@Override
	public  List<ProfilePicture> getProfileByStaffId(int staffid) {
	
		 List<ProfilePicture> bystaffId = profilePictureRepo.findBystaffId(staffid);
		
		return bystaffId;
	}


	@Override
	public  List<ProfilePicture> getProfileByRetailerId(int retailerid) {
		
		List<ProfilePicture> byretailerId = profilePictureRepo.findByretailerId(retailerid);
		
		return byretailerId;
	}

	@Override
	public  List<ProfilePicture> getProfileByDistributorId(int distributorid) {
		
		 List<ProfilePicture> bydistributorId = profilePictureRepo.findBydistributorId(distributorid);
		
		return bydistributorId;
	}
	
	@Override
	public List<ProfilePicture> getProfileBySupplierId(int supplierid) {
		
		List<ProfilePicture> bysupplierId = profilePictureRepo.findBysupplierId(supplierid);
		
		return bysupplierId;
	}
	 
	@Override
	public String update(int id,ProfilePictureDto profilePictureDto,int staffid,int retailerid,int distributorid,int supplierid,MultipartFile file) throws IOException {

		ProfilePicture profilePicture = profilePictureRepo.findById(id).get();
		profilePicture.setStaffId(staffid);
		profilePicture.setDistributorId(distributorid);
		profilePicture.setRetailerId(retailerid);
		profilePicture.setSupplierId(supplierid);
		ProfilePicture update = profilePictureRepo.save(profilePicture);
		
		String profilepicpath =  "profile_" + update.getId() +  file.getOriginalFilename();
		
		if(file.isEmpty())
		{
			profilePicture.setProfilepicname(profilePictureDto.getProfilepicname());
			profilePicture.setProfilepiclocation(profilePictureDto.getProfilepiclocation());
			profilePicture.setStaffId(profilePictureDto.getStaffId());
			profilePicture.setRetailerId(profilePictureDto.getRetailerId());
			profilePicture.setDistributorId(profilePictureDto.getDistributorId());
			profilePicture.setSupplierId(profilePictureDto.getSupplierId());
		}
		if(!file.isEmpty())
		{
			try {
				Path path = Paths.get(FILE_PATH + update.getProfilepiclocation());
				Files.delete(path);
			}catch (Exception e) {
				e.getStackTrace();
			}
			
			
			profilePicture.setProfilepicname(file.getOriginalFilename());
			profilePicture.setStaffId(staffid);
			profilePicture.setRetailerId(retailerid);
			profilePicture.setDistributorId(distributorid);
			profilePicture.setProfilepiclocation("profilepic/" + profilepicpath);
			
			Files.copy(file.getInputStream(), fileStorageLocation.resolve(profilepicpath),StandardCopyOption.REPLACE_EXISTING);
		}
		
		return "updated";
	}







}
