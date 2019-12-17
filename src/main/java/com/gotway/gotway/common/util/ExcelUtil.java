package com.gotway.gotway.common.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class ExcelUtil {


	public static Map<String,String> importExcel(InputStream in, String headImgFileName) {
		
		try {

			//判断是xls,还是xlsx
			boolean is03Excel = headImgFileName.matches("^.+\\.(?i)(xls)$");
			Workbook workbook = is03Excel ? new HSSFWorkbook(in):new XSSFWorkbook(in);
			Sheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			//读出数据，
			Map<String,String> map =null;
			for(int i=1 ;i<rows;i++){
				Row row = sheet.getRow(i);
				if(map==null)map= new HashMap<String,String>();
				
				Cell cell1 = row.getCell(0);
				Cell cell2 = row.getCell(1);
				if(cell1!=null && cell2!=null)map.put(cell1.getStringCellValue(),cell2.getStringCellValue());

			}
			return map;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
