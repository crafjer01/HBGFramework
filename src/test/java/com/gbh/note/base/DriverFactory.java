package com.gbh.note.base;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

	public static WebDriver create(String driver) {
		
		if(driver.equalsIgnoreCase("chrome")) {
			return Chrome.getDriver();
		}
		if(driver.equalsIgnoreCase("firefox")) {
			return Firefox.getDriver();
		}
		
		return null;
	}

	public static WebDriver create(String driver, boolean headless) {

		if (driver.equalsIgnoreCase("chrome")) {
			
			return Chrome.getDriver(headless);
		}
		if (driver.equalsIgnoreCase("firefox")) {
			
			return Firefox.getDriver(headless);
		}

		return null;

	}

}
