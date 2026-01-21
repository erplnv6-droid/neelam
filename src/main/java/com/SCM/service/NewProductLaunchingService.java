package com.SCM.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.NewProductLaunchingDto;
import com.SCM.model.NewProductLaunching;

public interface NewProductLaunchingService {

    String uploadimage(MultipartFile file,NewProductLaunchingDto launchingDto) throws IOException;
      
    List<NewProductLaunching> fetchAll() throws IOException;
    
    String updateuploadimage(MultipartFile file,int id) throws IOException;
	
	InputStream getResourceFile(String path,String name) throws FileNotFoundException;
	
	void deleteproductlaunching(int id);
}
