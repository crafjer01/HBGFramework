package com.gbh.note.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class ExcelReader {
	static Workbook book;
	static Sheet sheet;

	public static String TESTDAT_SHEET_PATH;
	
	public ExcelReader(String directory) {
		TESTDAT_SHEET_PATH = directory;
	}
		
	@DataProvider(name="dp")
	public  static Object[][] getData(Method m){
		
		String sheetName = m.getName();
		
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDAT_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			book =  WorkbookFactory.create(file);
			
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		sheet = book.getSheet(sheetName);
		
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			
			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				
				data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
			}
		}
		
		return data;
	}
	
	
}
