package com.servicenow.impl.register;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.servicenow.common.AbstractPageObject;
import com.servicenow.impl.account.AccountSelenium2Impl;
import com.servicenow.impl.login.LoginPage;
/**
 * 
 * @author prasannanjaneyulu padavala 
 *
 */
public class RegisterPage extends AbstractPageObject {
	Logger logger = Logger.getLogger(RegisterPage.class);
	
	public boolean registerNewAccount(String sLoginname, String sEmail,String sPassword){
		navigateToPage();
		typeEditBox("login", sLoginname);
		typeEditBox("email", sEmail);
		typeEditBox("password", sPassword);
		typeEditBox("confirmPassword", sPassword);
		clickButton("Register");
		
		if(isTextPresent("Registration failed!")){
			logger.error("Failed to register");
			return false;
		}
		//Registration is failing need to see the success message so that, i can have better
		//validation step here
		return true;
	}

	@Override
	public void assertInPage() {
		Assert.assertNotNull(waitForElementVisibility(By.xpath("//h1[text()='Registration']")));
	}

	@Override
	public void navigateToPage() {
		openBrowser();
		driver.get(envprops.getProperty("url"));
		if(LoginPage.isloggedin){
			new AccountSelenium2Impl().logout();
			LoginPage.isloggedin = false;
		}
		WebElement registerlink = waitForElementVisibility(By.linkText("Register a new account"));
		if(registerlink!=null)
		{
			registerlink.click();
		}
		assertInPage();
	}
}
