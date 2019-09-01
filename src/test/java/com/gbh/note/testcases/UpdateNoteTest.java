package com.gbh.note.testcases;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.gbh.note.base.BaseTest;
import com.gbh.note.utilities.ExcelReader;

public class UpdateNoteTest extends BaseTest {

	@Test(dataProviderClass = ExcelReader.class, dataProvider = "dp")
	public void updateNoteTest(String title, String message, String runmode) throws InterruptedException {
	
		
		// mode test, Y = make test, N = Do not make test
		if (!runmode.equalsIgnoreCase("Y")) {
			throw new SkipException("Saltando la prueba: addCustomerTest, pues el mode ejecuci�n esta apagado (N).");
		}
		
		click("noteCreated");
		type("input", title);
		type("textarea", message);
		click("btnDone");
	}

}
