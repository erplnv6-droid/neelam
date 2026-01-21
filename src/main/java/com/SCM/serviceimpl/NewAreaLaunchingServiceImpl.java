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

import com.SCM.model.NewAreaLaunching;
import com.SCM.repository.NewAreaLaunchingRepo;
import com.SCM.service.NewAreaLaunchingService;

@Service
public class NewAreaLaunchingServiceImpl implements NewAreaLaunchingService {

	@Autowired
	private NewAreaLaunchingRepo newAreaLaunchingRepo;
	
	@Value("${FILE_PATH}")
	private String FILE_PATH;
	
	private final Path fileStorageLocation;
	
	@Autowired
	public NewAreaLaunchingServiceImpl(Environment env) throws IOException {

		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir3", "/Areaimg/")).toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation);	
	}

	
	@Override
	public String uploadimage(MultipartFile file) throws IOException {
		
       String filename = "Area_image_"+ file.getOriginalFilename();
		
		Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(filename),StandardCopyOption.REPLACE_EXISTING);
		
		NewAreaLaunching launching = new NewAreaLaunching();
		launching.setName(filename);
		launching.setLocation("Areaimg/" + filename);
		
		launching.setCreateddate(LocalDate.now());
		launching.setCreatedtime(LocalTime.now());
		
		newAreaLaunchingRepo.save(launching);
		
		return "file uploaded";
	}

	
	@SuppressWarnings("unused")
	@Override
	public String updateuploadimage(MultipartFile file, int id) throws IOException {
	 
		NewAreaLaunching areaLaunching = newAreaLaunchingRepo.findById(id).get();
		
		Path filetodeletepath = Paths.get(FILE_PATH + areaLaunching.getLocation());
		
		if(areaLaunching != null)
		{
			if(filetodeletepath != null)
			{
				Files.delete(filetodeletepath);
			}
			
			String filename = "area_image "+ file.getOriginalFilename();
			
			Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(filename),StandardCopyOption.REPLACE_EXISTING);
			
			areaLaunching.setName(file.getOriginalFilename());
			areaLaunching.setLocation("Areaimg/" + file.getOriginalFilename());
			
			newAreaLaunchingRepo.save(areaLaunching);
			
		}else {
						
			String filename = "area_image "+ file.getOriginalFilename();
			
			Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(filename),StandardCopyOption.REPLACE_EXISTING);
			
			NewAreaLaunching newAreaLaunching = new NewAreaLaunching();
			newAreaLaunching.setName(file.getOriginalFilename());
			newAreaLaunching.setLocation("Areaimg/" + file.getOriginalFilename());
			
			newAreaLaunchingRepo.save(newAreaLaunching);
		}
		
		return "image updated";
	}

	
	@Override
	public InputStream getResourceFile(String path, String name) throws FileNotFoundException {
		
		String filepath = fileStorageLocation + File.separator + name;
		
		return new FileInputStream(filepath);
	}


	@Override
	public List<NewAreaLaunching> fetchAll() {
		
		return newAreaLaunchingRepo.findAll();
	}


	@Override
	public void deletearealunch(int id) {
		
		newAreaLaunchingRepo.deleteById(id);
		
	}

}
