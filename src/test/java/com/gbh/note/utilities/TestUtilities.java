package com.gbh.note.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.gbh.note.base.BaseTest;

public class TestUtilities extends BaseTest {

	public static String imgName = "";

	public static void captureScreenshot() throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		imgName = new Date().toString().replace(":", "_").replace(" ", "_") + ".jpg";
		System.out.println(imgName);
		FileUtils.copyFile(scrFile, new File(getRootPath() + "target\\surefire-reports\\html\\" + imgName));
	}

	public static String getResourcesPath() {
		return System.getProperty("user.dir") + "\\src\\test\\resources\\";
	}

	public static String getRootPath() {
		return System.getProperty("user.dir") + "\\";
	}

	public static boolean isTestRunnable(String testName) {
	
		String sheetName = "test_suite";

		String TESTDAT_SHEET_PATH = getResourcesPath() + "excel\\notes.xlsx";

		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDAT_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			excel.book = WorkbookFactory.create(file);

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		excel.sheet = excel.book.getSheet(sheetName);

		for (int i = 0; i < excel.sheet.getLastRowNum(); i++) {
			String tescase = excel.sheet.getRow(i + 1).getCell(0).toString();

			if (tescase.equalsIgnoreCase(testName)) {
				String runmode = excel.sheet.getRow(i + 1).getCell(1).toString();

				if (runmode.equalsIgnoreCase("Y")) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;

	}
	
	public static String toCapilizeCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	
}
