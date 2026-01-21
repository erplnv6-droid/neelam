package com.SCM.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.NewProductLaunchingDto;
import com.SCM.model.NewProductLaunching;
import com.SCM.repository.NewProductLaunchingRepo;
import com.SCM.service.NewProductLaunchingService;

@Service
public class NewProductLaunchingServiceImpl implements NewProductLaunchingService {

	@Autowired
	private NewProductLaunchingRepo newProductLaunchingRepo;
		
	@Value("${FILE_PATH}")
	private String FILE_PATH;
	
	private final Path fileStorageLocation;
	
	@Autowired
	public NewProductLaunchingServiceImpl(Environment env) throws IOException {
	
		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir2", "/Launchingimag/")).toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation);	
	}
    

	
	@Override
	public String uploadimage(MultipartFile file,NewProductLaunchingDto launchingDto) throws IOException {
			
		String filename = "launch_image "+ file.getOriginalFilename();
		
		Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(filename),StandardCopyOption.REPLACE_EXISTING);
		
		NewProductLaunching launching = new NewProductLaunching();
		launching.setName(filename);
		launching.setLocation("Launchingimag/" + filename);
		launching.setProductid(launchingDto.getProductid());
		launching.setCreateddate(LocalDate.now());
		launching.setCreatedtime(LocalTime.now());
		
		newProductLaunchingRepo.save(launching);
		
		return "file uploaded";
	}

	
	@Override
	public InputStream getResourceFile(String path, String name) throws FileNotFoundException {
		
		String filepath = fileStorageLocation + File.separator + name;
		
		return new FileInputStream(filepath);
	}


	@SuppressWarnings("unused")
	@Override
	public String updateuploadimage(MultipartFile file,int id) throws IOException {
		
		NewProductLaunching launching = newProductLaunchingRepo.findById(id).get();
		
		Path filetodeletepath = Paths.get(FILE_PATH + launching.getLocation());
		
		if(launching != null)
		{
//			if(filetodeletepath != null)
//			{
//				Files.delete(filetodeletepath);
//			}
			
			String filename = "launch_image "+ file.getOriginalFilename();
			
//			Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(filename),StandardCopyOption.REPLACE_EXISTING);
			
			launching.setName(file.getOriginalFilename());
			launching.setLocation("Launchingimag/" + file.getOriginalFilename());
			
			newProductLaunchingRepo.save(launching);
			
		}else {
						
			String filename = "launch_image "+ file.getOriginalFilename();
			
//			Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(filename),StandardCopyOption.REPLACE_EXISTING);
			
			NewProductLaunching newProductLaunching = new NewProductLaunching();
			newProductLaunching.setName(file.getOriginalFilename());
			newProductLaunching.setLocation("Launchingimag/" + file.getOriginalFilename());
			
			newProductLaunchingRepo.save(newProductLaunching);
		}
		
		return "image updated";
	}


	@Override
	public List<NewProductLaunching> fetchAll() throws IOException {
		
		return newProductLaunchingRepo.findAll();
	}



	@Override
	public void deleteproductlaunching(int id) {
		
		newProductLaunchingRepo.deleteById(id);
		
	}



}

