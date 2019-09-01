package com.gbh.note.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.gbh.note.base.BaseTest;
import com.gbh.note.utilities.TestUtilities;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends BaseTest implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		test = rep.startTest(result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(LogStatus.INFO.PASS, 
				TestUtilities.toCapilizeCase(result.getName()) + " Pasaron");
		rep.endTest(test);
		rep.flush();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
	
		test.log(LogStatus.FAIL, result.getName().toUpperCase() + " Falló con la Excepción "+ result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtilities.imgName));
		
		try {
			TestUtilities.captureScreenshot();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Reporter.log("Click para ver el screenshot");
		Reporter.log("<a target='_blank' href='"+ TestUtilities.imgName +"'>Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target='_blank' href='"+ TestUtilities.imgName +"'><img width='300' height='250' src='"+TestUtilities.imgName+"'/></a>");
		
		rep.endTest(test);
		rep.flush();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.log(LogStatus.SKIP, "Saltando la prueba: " + 
						result.getName().toUpperCase() 
					+", pues el mode ejecución esta apagado (N).");
		rep.endTest(test);
		rep.flush();
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
