package com.gbh.note.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.gbh.note.utilities.TestUtilities;

public class Chrome {

	public static WebDriver getDriver() {
		System.setProperty("webdriver.chrome.driver", TestUtilities.getResourcesPath() + "executables\\chromedriver.exe");
		
		return new ChromeDriver();
	}
	public static WebDriver getDriver(boolean headless) {
		System.setProperty("webdriver.chrome.driver", TestUtilities.getResourcesPath() + "executables\\chromedriver.exe");
		ChromeOptions ChromeOptions = new ChromeOptions();
		ChromeOptions.setHeadless(true);
		return new ChromeDriver(ChromeOptions);
	}
	
	

}
