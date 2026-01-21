package com.SCM.Helper;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.model.WarehouseExcelUpload;

@Component
public class ExcelUploadByLoop {

	public static boolean checkExcelFormat(MultipartFile multi) {

		String contentType = multi.getContentType();

		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {

			return true;
		}

		else {
			return false;
		}
	}

	public List<WarehouseExcelUpload> convertExcelUpload(InputStream is) throws IOException {

		List<WarehouseExcelUpload> notUploadedSongList1 = new ArrayList<WarehouseExcelUpload>();

		try {

			XSSFWorkbook workbook = new XSSFWorkbook(is);

			XSSFSheet sheet = workbook.getSheet(workbook.getSheetName(workbook.getActiveSheetIndex()));

			Row row;

			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // points to the starting of excel i.e excel first row
				row = (Row) sheet.getRow(i);

				WarehouseExcelUpload excel = new WarehouseExcelUpload();

				List<WarehouseExcelUpload> excelUploads = new ArrayList<WarehouseExcelUpload>();

				// index 0
				// work.........................................................................................

				String productname;

				if (row.getCell(0) == null) {
					productname = "";
				} else {
					productname = row.getCell(0).toString();
				}

				// index 1
				// dlppcs............................................................................................

				String dlppcs;
				if (row.getCell(1) == null) {
					dlppcs = "";
				} else {
					dlppcs = row.getCell(1).toString();
				}

				// index 2 language
				// ........................................................................................

				String dlpkg;
				if (row.getCell(2) == null) {
					dlpkg = "";
				} else {
					dlpkg = row.getCell(2).toString();
				}

				// For Correct
				// Data........................................................................

				excel.setProductname(productname);
				excel.setDlppcs(dlppcs);
				excel.setDlpkg(dlpkg);

				excelUploads.add(excel);
				
				notUploadedSongList1.addAll(excelUploads);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return notUploadedSongList1;

	}

}
