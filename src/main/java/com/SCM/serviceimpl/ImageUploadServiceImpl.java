package com.SCM.serviceimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.service.ImageUploadService;
import com.SCM.storage.StorageException;
import com.SCM.storage.StorageProperties;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {
	
	private final Path rootLocation;
	
	
	
	@Autowired
	public ImageUploadServiceImpl(StorageProperties storageProperties) {
		super();
		
		this.rootLocation = Paths.get(storageProperties.getLocation());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		try {
			Files.createDirectories(rootLocation);
		}catch(IOException e){
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public void save(MultipartFile file) {
		// TODO Auto-generated method stub
		
		try {
			
				Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
				try(InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
				}
		}catch(IOException e) {
			throw new StorageException("Failed to upload file",e);
		}
	}
}
