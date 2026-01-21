package com.SCM.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.ProductImageDto;
import com.SCM.model.ProductImage;
import com.SCM.repository.ProductImageRepo;
import com.SCM.service.ProductImageService;

@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private ProductImageRepo productImageRepo;

	private final Path fileStorageLocation;
    
	private final String FOLDER_PATH = "D:/IMG2/";
	
	
	@Autowired
	public ProductImageServiceImpl(Environment env) throws IOException {

		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files")).toAbsolutePath()
				.normalize();
		Files.createDirectories(this.fileStorageLocation);
	}

	

	@Override
	public ProductImage save(ProductImageDto proImage) {

		ProductImage image = new ProductImage();

		return productImageRepo.save(image);
	}

	
	@Override
	public ProductImage save1(MultipartFile files) throws IllegalStateException, IOException {
		
		ProductImage data = new ProductImage();

		Path targetLocation = this.fileStorageLocation.resolve(files.getOriginalFilename());
		Files.copy(files.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

		data.setName(files.getOriginalFilename());
		data.setLocation(targetLocation.toString());
		
		return productImageRepo.save(data);
	}


	
	private String getFileExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		String[] fileNameParts = fileName.split("\\.");

		return fileNameParts[fileNameParts.length - 1];
	}

	@Override
	public byte[] downloadImages(String filename) throws IOException {

		Optional<ProductImage> imgfile = productImageRepo.findByName(filename);
		String filepath = imgfile.get().getLocation();
		byte[] images = Files.readAllBytes(new File(filepath).toPath());
		return images;
	}

	@Override
	public List<ProductImage> getAllImage() {

		return productImageRepo.findAll();
	}

	@Override
	public String save2(MultipartFile files) throws IllegalStateException, IOException {

		String filename = new Date().getTime() + "-file." + getFileExtension(files.getOriginalFilename());

		Path targetLocation = this.fileStorageLocation.resolve(filename);
		Files.copy(files.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		
		return filename;
	}



	@Override
	public List<ProductImage> getImagePath() {
		// TODO Auto-generated method stub
		return null;
	}

}
