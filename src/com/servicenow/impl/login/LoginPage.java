package com.servicenow.impl.login;

import org.openqa.selenium.By;
import org.testng.log4testng.Logger;

import com.servicenow.common.AbstractPageObject;
import com.servicenow.impl.account.AccountSelenium2Impl;
/**
 * 
 * @author prasannanjaneyulu padavala 
 *
 */
public class LoginPage extends AbstractPageObject{
	public static boolean isloggedin = false;
	public static String loggedinuser;
	Logger logger = Logger.getLogger(LoginPage.class);

	public boolean login(String sUsername, String sPassword){
		if(!isloggedin){
			navigateToPage();
			clickLink("login");
			if(waitForElement(By.xpath("//h1[text()='Authentication']"))!=null)
			{
				typeEditBox("username", sUsername);
				typeEditBox("password", sPassword);
				clickButton("Authenticate");
				if(waitForElementVisibility(By.xpath("//h1[text()='Welcome to Gurukula!']"))!=null){
					logger.info("Successfully Logged into the Application");
					loggedinuser = sUsername;
					isloggedin = true;
					return true;
				}
			}
			logger.error("Unable to Navigate to Authentication login page");			
			return false;
		}
		else
		{
			logger.warn("User: "+sUsername+" is already logged in");			
			return false;
		}
	}

	public boolean invalidLogin(String sUsername, String sPassword){
		navigateToPage();
		if(isloggedin)
		{
			new AccountSelenium2Impl().logout();
			isloggedin = false;
		}
		clickLink("login");
		if(waitForElement(By.xpath("//h1[text()='Authentication']"))!=null)
		{
			typeEditBox("username", sUsername);
			typeEditBox("password", sPassword);
			clickButton("Authenticate");
			if(isTextPresent("Authentication failed!"))
				return true;
		}

		logger.error("Authentication has to be failed. But it couldn't failed.Please see screenshot for more details");			
		return false;
	}

	@Override
	public void assertInPage() {
	}

	@Override
	public void navigateToPage() {
		openBrowser();
		driver.get(envprops.getProperty("url"));
		driver.manage().window().maximize();
	}
}
