package com.SCM.barcodeUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;

import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.model.Group;
import com.SCM.model.Product;
import com.SCM.model.ProductBarcode;
import com.SCM.repository.GroupRepository;
import com.SCM.repository.ProductBarcodeRepo;
import com.SCM.repository.ProductRepo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lombok.experimental.var;
@Service
public class QrCodeGenerator {

//	private final ProductBarcodeRepo barcodeRepo;
	

	
	
	@Autowired ProductRepo productRepo;
	

//
	public void generateQrCode(int id) throws WriterException, IOException {

//	    byte[] qrCodeBytes = null;
	    
		try {
		    String barcodePath = "E:\\New folder\\";
		    Product product=productRepo.findById(id).get();
		    String barcodeName = barcodePath + product.getProductName() + product.getId() + "-CODE128.png";
		    
		    Code128Writer writer = new Code128Writer();
		    BitMatrix matrix = writer.encode("ID: " + product.getId() +
		            "\n" + "productEanCode: " + product.getEanCode() ,
		            BarcodeFormat.CODE_128, 500, 300);
		    Path path = Paths.get(barcodeName);
		    MatrixToImageWriter.writeToPath(matrix, "PNG", path);

		}
		catch (Exception e) {
		    System.out.println("Error while creating barcode"+e);
		}
	}
//		
//		
		
//	
//	@Autowired
//	private GroupRepository groupRepository;
//		public ProductBarcode generateQrCode(Product product) throws WriterException, IOException {
////		    byte[] qrCodeBytes = null;
//			try {
//			    String barcodePath = "E:\\New folder\\";
//			    String barcodeName = barcodePath + product.getProductName() + product.getId() + "-CODE128.png";
//			    Code128Writer writer = new Code128Writer();
////			    Group group=new Group();
////			    List<Group> findAll = groupRepository.findAll();
////			    System.out.println(findAll);
//			    BitMatrix matrix = writer.encode("ID: " + product.getId() +
//			            "\n" + "ProductName: " + product.getProductName() +
//			            "\n" + "Code " + product.getHsnCode() +
//			            "\n" + "type " + product.getProductType(),
//			            BarcodeFormat.CODE_128, 500, 300);
//			    Path path = Paths.get(barcodeName);
//			    MatrixToImageWriter.writeToPath(matrix, "PNG", path);
//			    ProductBarcode barcode=new ProductBarcode();
////		        qrCodeBytes = Files.readAllBytes(path);
////		        barcode.setImages(qrCodeBytes);
//		        barcode.setName(product.getProductName());
////		        barcodeRepo.save(barcode);
//		        return barcode;
//	   
//			}
//			catch (Exception e) {
//			    System.out.println("Error while creating barcode"+e);
//			    return null;
//			}
//		
//
//	}
	
		
	

}
