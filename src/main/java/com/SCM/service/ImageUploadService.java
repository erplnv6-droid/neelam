package com.SCM.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
	void init();
	public void save(MultipartFile file);
}
