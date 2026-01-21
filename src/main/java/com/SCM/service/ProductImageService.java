package com.SCM.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.ProductImageDto;
import com.SCM.model.ProductImage;

public interface ProductImageService {
	
	public ProductImage save(ProductImageDto proImageDto);
	
	public String save2(MultipartFile files) throws IllegalStateException, IOException;
	
	public ProductImage save1(MultipartFile file) throws IllegalStateException, IOException;
	
	public byte[] downloadImages(String filename) throws IOException;
	
	public List<ProductImage> getAllImage();
	
	public List<ProductImage> getImagePath();
	
}
 