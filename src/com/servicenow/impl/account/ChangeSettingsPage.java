package com.servicenow.impl.account;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.servicenow.common.AbstractPageObject;
/**
 * 
 * @author prasannanjaneyulu padavala
 * This page contains implementations for the functionalities one can carry out on Change Settings Page
 */
public class ChangeSettingsPage extends AbstractPageObject{
	
	Logger logger = Logger.getLogger(ChangeSettingsPage.class);
	
	public boolean changeSettings(String firstname, String lastname, String email, String language) {
		navigateToPage();
		typeEditBox("firstName", firstname);
		typeEditBox("lastName", lastname);
		typeEditBox("email", email);
		selectByNameorID("langKey", language);
		if(!isTextPresent("Settings saved!"))
		{
			logger.error("Failed to Change Settings. Please see screenshot for more details");
			return false;
		}
		return true;
	}

	@Override
	public void assertInPage() {
		if(waitForElementVisibility(By.xpath("//h2[starts-with(text(),'User settings for')]"))!=null)
			return;
		else
			logger.error("Unable to navigate to Staffs Page");
	}

	@Override
	public void navigateToPage() {
		WebElement account = waitForElementVisibility(By.xpath("//span[text()='Account']/ancestor::a"));
		if(account!=null){
			account.click();
			WebElement settings = waitForElementToBeClickable(By.xpath("//a[@ui-sref='settings']"));
			Assert.assertNotNull("Settings element under Account menu doesn't exist", settings);
			settings.click();
			assertInPage();
		}
		else
			logger.error("Unable to navigate to homepage");	
	}

}
