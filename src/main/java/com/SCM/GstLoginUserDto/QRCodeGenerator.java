//package com.SCM.GstLoginDto;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.SCM.model.Product;
//import com.SCM.model.Sales;
//import com.SCM.repository.ProductRepo;
//import com.SCM.repository.SalesRepo;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.oned.Code128Writer;
//@Service
//public class QRCodeGenerator {
//	
//	
//	@Autowired
//	static SalesRepo salesRepo;
//
//
//		  
//		  public static byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
//		        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
//		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
//		        return outputStream.toByteArray();
//	 
//	     
//}
//		
//		  
//		  
//	  }

