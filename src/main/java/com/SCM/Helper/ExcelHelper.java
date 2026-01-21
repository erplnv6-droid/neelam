package com.SCM.Helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.model.WarehouseExcelUpload;
import com.SCM.repository.WarehouseExcelUploadRepo;

@Component
public class ExcelHelper {

	@Autowired
	WarehouseExcelUploadRepo excelUploadRepo;

	public static boolean CheckExcelFormat(MultipartFile file) {
	
		String contentType = file.getContentType();
		
		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			
			return true;
		} else {
			return false;
		}
	}

	
	public List<WarehouseExcelUpload> ConvertExcelToListOfSecondary(InputStream is) {
		
		List<WarehouseExcelUpload> list = new ArrayList<>();

		try {
			
			XSSFWorkbook Workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = Workbook.getSheet(Workbook.getSheetName(Workbook.getActiveSheetIndex()));

			int rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();

			while (iterator.hasNext()) {
				Row row = iterator.next();

				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cells = row.iterator();
				int cid = 0;
				WarehouseExcelUpload s = new WarehouseExcelUpload();
				
				while (cells.hasNext()) {
					Cell cell = cells.next();

					switch (cid) {
					case 0:
						if (cell != null && cell.getCellType() == CellType.STRING) {
							s.setProductname(cell.getStringCellValue());
						} else {
							s.setProductname(null);
						}
						break;
					case 1:
						if (cell != null && cell.getCellType() == CellType.STRING) {
							s.setDlppcs(cell.toString());
							System.out.println(s.getDlppcs() + "+++++++++  pcs");
						} else {
							s.setDlppcs(null);
							
							System.out.println(s.getDlppcs() + "=========");
						}
						break;
					case 2:{
						if(cell != null && cell.getCellType() == CellType.STRING) {
							s.setDlpkg(cell.getStringCellValue());
							System.out.println(s.getDlpkg() + "+++++++++  kg");
						}else {
							s.setDlpkg(null);
							System.out.println(s.getDlpkg() + "==============");
						}
                        break;
					}
					
					default:
						break;
					}
					cid++;
				}
				
				list.add(s);
				System.out.println(s);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list);
		return list;
	}
}
