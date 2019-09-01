package com.gbh.note.utilities;

import java.io.File;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	private static ExtentReports report;
	
	public static ExtentReports getInstance() {
		if(report == null) {
			report = new ExtentReports(TestUtilities.getRootPath() + "target\\surefire-reports\\html\\extentReport.html", true, DisplayOrder.OLDEST_FIRST);
			report.loadConfig(new File(TestUtilities.getResourcesPath() + "extentConfig\\ReportsConfig.xml"));
		}
		
		return report;
	}
}
