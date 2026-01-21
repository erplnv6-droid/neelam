//package com.SCM.controller;
//
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.SCM.barcodeUtils.ImageUtils;
//import com.SCM.barcodeUtils.QrCodeGenerator;
//import com.SCM.model.Group;
//import com.SCM.model.Product;
//import com.SCM.model.ProductBarcode;
//import com.SCM.repository.GroupRepository;
//import com.SCM.repository.ProductBarcodeRepo;
//import com.SCM.repository.ProductRepo;
//import com.SCM.serviceimpl.QrImageServiceImpl;
//import com.google.zxing.WriterException;
//
//@RestController
//@RequestMapping("/api/productbarcode")
//public class QrImageController {
//
//	@Autowired
//	private QrImageServiceImpl imageServiceImpl;
//	@Autowired
//	private ProductBarcodeRepo qrImagesRepository;
//	
//	@Autowired
//	private ProductRepo productRepo;
//	
//	@Autowired
//	ProductBarcodeRepo barcodeRepo;
//	@Autowired
//	private GroupRepository groupRepository;
//	@PostMapping("/generate/{id}")
//	
//	public String generateBarcode(@PathVariable int id) throws WriterException, IOException {
//		
//		QrCodeGenerator codeGenerator=new QrCodeGenerator();
//		
//		Product product=productRepo.findById(id).get();
//		
//		ProductBarcode productBarcode = codeGenerator.generateQrCode(product);
//		
//		
//		List<Group> groups = groupRepository.findAll();
//		for(long i=1;i<=groups.size();i++) {
//			Group groups1 = groupRepository.findById(i).get();
//			ProductBarcode generateQrCodeForGroup = codeGenerator.generateQrCodeForGroup(groups1, product);
//			generateQrCodeForGroup.setProductid(id);
//			generateQrCodeForGroup.setGroupid(groups1.getId());
//			barcodeRepo.save(generateQrCodeForGroup);
//			
//		}
//		System.out.println(groups+"ggggggggggggggggggggggggggggggggggggg");
//		barcodeRepo.save(productBarcode);
//
////		ProductBarcode generateQrCodeForGroup = codeGenerator.generateQrCodeForGroup(groups, product);
//		
//		return "succesfully generated";
//	}
//	
//	@GetMapping("/{id}")
//	public ResponseEntity<byte[]> getImage(@PathVariable long id){
////		byte[] imageData=qrImagesRepository.findById(id).get().getImages();
////		
////		HttpHeaders headers=new HttpHeaders();
////		headers.setContentType(MediaType.IMAGE_JPEG);
////		return new ResponseEntity<byte[]>(imageData,headers,HttpStatus.OK);
//		return null;
//		
//	}
//	
//}
