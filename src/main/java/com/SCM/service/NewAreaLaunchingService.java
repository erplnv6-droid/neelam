package com.SCM.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.model.NewAreaLaunching;

public interface NewAreaLaunchingService {

    String uploadimage(MultipartFile file) throws IOException;
    
    List<NewAreaLaunching> fetchAll();
    
    String updateuploadimage(MultipartFile file,int id) throws IOException;
	
	InputStream getResourceFile(String path,String name) throws FileNotFoundException;
	
	void deletearealunch(int id);
}
