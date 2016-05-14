package com.servicenow.impl.account;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.servicenow.common.AbstractPageObject;
/**
 * 
 * @author prasannanjaneyulu padavala
 * This page contains implementations for the functionalities one can carry out on Change Passowrd Page
 *
 */

public class ChangePasswordPage extends AbstractPageObject {
	Logger logger = Logger.getLogger(ChangePasswordPage.class);
	
	
	public boolean changePassword(String newpassword){
		navigateToPage();
		typeEditBox("password", newpassword);
		typeEditBox("confirmPassword", newpassword);
		clickButton("Save");
		if(isTextPresent(" The password could not be changed.")){
			logger.error("Failed to change the password!!");
			return false;
		}
		return true;
	}
	
	@Override
	public void assertInPage() {
		if(waitForElementVisibility(By.xpath("//h2[starts-with(text(),'Password for')]"))!=null)
			return;
		else
			logger.error("Unable to navigate to Staffs Page");

	}

	@Override
	public void navigateToPage() {
		WebElement account = waitForElementVisibility(By.xpath("//span[text()='Account']/ancestor::a"));
		if(account!=null){
			account.click();
			WebElement password = waitForElementToBeClickable(By.xpath("//a[@ui-sref='password']"));
			Assert.assertNotNull("password element under Account menu doesn't exist", password);
			password.click();
			assertInPage();
		}
		else
			logger.error("Unable to navigate to homepage");
	}

}
