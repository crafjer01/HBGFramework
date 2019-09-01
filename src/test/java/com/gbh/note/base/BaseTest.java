package com.gbh.note.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.gbh.note.types.ElementType;
import com.gbh.note.utilities.ExcelReader;
import com.gbh.note.utilities.ExtentManager;
import com.gbh.note.utilities.TestUtilities;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.sukgu.Shadow;

public class BaseTest {

	protected static WebDriver driver;
	protected static Properties rulesLocation = null;
	protected static Properties config = null;
	protected static FileInputStream fis = null;
	protected static ExcelReader excel = new ExcelReader(TestUtilities.getResourcesPath() + "excel\\notes.xlsx");
	protected static WebDriverWait wait;
	protected static ExtentReports rep = ExtentManager.getInstance();
	protected static ExtentTest test;
	protected static JavascriptExecutor js = null;
	protected static Shadow shadow = null;

	@BeforeSuite
	protected void setUp() {

		if (driver == null) {

			// load properties
			config = getConfig();
			rulesLocation = getRulesLocation();

			// webdriver
			driver = DriverFactory.create(config.getProperty("browser"));
			wait = new WebDriverWait(driver, 5);
			driver.get(config.getProperty("siteUrl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// Javascript
			js = (JavascriptExecutor) driver;

			// shadow dom
			shadow = new Shadow(driver);

		}

	}

	@AfterSuite
	protected void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	private Properties getConfig() {
		Properties config = new Properties();
		String filepath = TestUtilities.getResourcesPath() + "properties\\Config.properties";

		try {
			fis = new FileInputStream(filepath);
			config.load(fis);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return config;
	}

	private Properties getRulesLocation() {
		Properties rulesLocation = new Properties();
		String filepath = TestUtilities.getResourcesPath() + "properties\\rulesLocation.properties";

		try {
			fis = new FileInputStream(filepath);
			rulesLocation.load(fis);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return rulesLocation;
	}

	protected boolean isElementPresent(By by) {
		try {

			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected void click(String element, ElementType type) {
		String el = rulesLocation.getProperty(element);
		System.out.println(el);
		getElement(el, type).click();
		test.log(LogStatus.INFO, "Clickeando en : " + element);
	}

	protected void waitAnClick(String element, ElementType type) {
		try {
			String el = rulesLocation.getProperty(element);
			System.out.println(el);
			By locator = getElementLocation(el, type);
			WebElement elementFind = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			elementFind.click();
			test.log(LogStatus.INFO, "Clickeando en : " + element);

		} catch (Exception e) {
			System.out.println("El elemento parece que no esta en la página.");
		}

	}

	protected void click(String locator) {
		String el = rulesLocation.getProperty(locator);
		WebElement element = shadow.findElement(el);
		js.executeScript("arguments[0].click();", element);
		test.log(LogStatus.INFO, "Clickeando en : " + element);
	}

	protected void type(String element, String s, ElementType type) {
		String el = rulesLocation.getProperty(element);
		getElement(el, type).sendKeys(s);
		test.log(LogStatus.INFO, "Escribiendo en : " + element);
	}

	protected void type(String element, String s) {
		String el = rulesLocation.getProperty(element);
		WebElement input = shadow.findElement(el);
		input.sendKeys(s);
		test.log(LogStatus.INFO, "Escribiendo en : " + element);
	}

	protected void select(String element, String value, ElementType type) {
		String el = rulesLocation.getProperty(element);
		WebElement ele = getElement(el, type);
		Select select = new Select(ele);
		test.log(LogStatus.INFO, "Seleccionando el : " + element);
		select.selectByVisibleText(value);
	}

	protected void validateAlert(String text) {
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(text));
		alert.accept();
		test.log(LogStatus.INFO, "Aceptando la alerta");
	}

	protected void isEquals(String actual, String expected) throws IOException {
		try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable t) {
			TestUtilities.captureScreenshot();

			// ReportNG
			Reporter.log("<br>" + "Verificacion fallida: " + t.getMessage() + "<br>");
			Reporter.log("<a target='_blank' href='" + TestUtilities.imgName + "'><img width='300' height='250' src='"
					+ TestUtilities.imgName + "'/></a>");
			Reporter.log("<br>");

			// Extent Report
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtilities.imgName));
			test.log(LogStatus.FAIL, " La Verificacion Falló con la Excepción " + t.getMessage());

		}
	}

	public static WebElement getElement(String locator, ElementType type) {

		WebElement element = null;

		switch (type) {
		case ID:
			System.out.println("Elemento encontrado con  id: " + locator);
			element = driver.findElement(By.id(locator));
			break;
		case NAME:
			System.out.println("Elemento encontrado con  name: " + locator);
			element = driver.findElement(By.name(locator));
			break;
		case CSS_SELECTOR:
			System.out.println(locator);
			element = driver.findElement(By.cssSelector(locator));
			System.out.println("Elemento encontrado con  css selector: " + locator);
			break;
		case LINKTEXT:
			System.out.println("Elemento encontrado con  link text: " + locator);
			element = driver.findElement(By.linkText(locator));
			break;
		case PARTIAL_LINK:
			System.out.println("Elemento encontrado con  partial link text: " + locator);
			element = driver.findElement(By.partialLinkText(locator));
			break;
		case TAGNAME:
			System.out.println("Elemento encontrado con  tag name: " + locator);
			element = driver.findElement(By.tagName(locator));
			break;
		case XPATH:
			System.out.println("Elemento encontrado con  xpath: " + locator);
			element = driver.findElement(By.xpath(locator));
			break;
		case CLASSNAME:
			System.out.println("Elemento encontrado con  class name: " + type);
			element = driver.findElement(By.className(locator));
			break;

		}

		return element;
	}

	public List<WebElement> getElements(String locator, ElementType type) {

		List<WebElement> elements = null;

		switch (type) {
		case ID:
			System.out.println("Elemento encontrado con  id: " + locator);
			elements = driver.findElements(By.id(locator));
			break;
		case NAME:
			System.out.println("Elemento encontrado con  name: " + locator);
			elements = driver.findElements(By.name(locator));
			break;
		case CSS_SELECTOR:
			System.out.println("Elemento encontrado con  css selector: " + locator);
			elements = driver.findElements(By.cssSelector(locator));
			break;
		case LINKTEXT:
			System.out.println("Elemento encontrado con  link text: " + locator);
			elements = driver.findElements(By.linkText(locator));
			break;
		case PARTIAL_LINK:
			System.out.println("Elemento encontrado con  partial link text: " + locator);
			elements = driver.findElements(By.partialLinkText(locator));
			break;
		case TAGNAME:
			System.out.println("Elemento encontrado con  tag name: " + locator);
			elements = driver.findElements(By.tagName(locator));
			break;
		case XPATH:
			System.out.println("Elemento encontrado con  xpath: " + locator);
			elements = driver.findElements(By.xpath(locator));
			break;
		case CLASSNAME:
			System.out.println("Elemento encontrado con  class name: " + type);
			elements = driver.findElements(By.className(locator));
			break;

		}

		return elements;
	}

	public static By getElementLocation(String locator, ElementType type) {

		By by = null;

		switch (type) {
		case ID:
			return By.id(locator);
		case NAME:
			return By.name(locator);
		case CSS_SELECTOR:
			return By.cssSelector(locator);
		case LINKTEXT:
			return By.linkText(locator);
		case PARTIAL_LINK:
			return By.partialLinkText(locator);
		case TAGNAME:
			return By.tagName(locator);
		case XPATH:
			return By.xpath(locator);
		case CLASSNAME:
			return By.className(locator);

		}

		return null;
	}
}
