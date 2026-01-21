package com.SCM.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.SCM.barcodeUtils.ImageUtils;
import com.SCM.model.ProductBarcode;
import com.SCM.repository.ProductBarcodeRepo;


@Service
public class QrImageServiceImpl {

	@Autowired
	private ProductBarcodeRepo qrImagesRepository;
	public byte[] downloadImage(long id) {
		Optional<ProductBarcode> imageData=qrImagesRepository.findById(id);
		
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		
		
//		byte[] images1=ImageUtils.compressImage(imageData.get().getImages());
//		byte[] images=ImageUtils.decompressImage(imageData.get().getImages());
//		return images;
		return null;
		
	}
  }
