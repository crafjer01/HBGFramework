package com.gbh.note.testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.gbh.note.base.BaseTest;
import com.gbh.note.types.ElementType;

public class LoginTest extends BaseTest {

	@Test
	public void LoginTest() {
		waitAnClick("noteApp", ElementType.TAGNAME);
		
		loginGmail("eduardopineda06", "crafjer0631");
	}

	public void loginGmail(String username, String password) {
		driver.findElement(By.id("identifierId")).sendKeys(username);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/span/span")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		WebElement pass = driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input"));
		wait.until(ExpectedConditions.elementToBeClickable(pass));
		pass.sendKeys(password);

		driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/span/span")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

}
