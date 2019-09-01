package com.gbh.note.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.gbh.note.utilities.TestUtilities;

public class Firefox {

	public static WebDriver getDriver() {
		System.setProperty("webdriver.gecko.driver", TestUtilities.getResourcesPath() + "executables\\geckodriver.exe");
		
		return new FirefoxDriver();
	}
	
	public static WebDriver getDriver(boolean headless) {
		System.setProperty("webdriver.gecko.driver", TestUtilities.getResourcesPath() + "executables\\geckodriver.exe");
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setHeadless(true);
		return new FirefoxDriver(firefoxOptions);
	}
	
	

}
