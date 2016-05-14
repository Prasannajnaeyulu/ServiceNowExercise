
package com.servicenow.impl.branch;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.servicenow.common.AbstractPageObject;
/**
 * 
 * @author prasannanjaneyulu padavala 
 * This page contains implementations for the functionalities one can carry out on Branch Page
 *
 */
public class BranchPage extends AbstractPageObject {
	/*	@FindBy(xpath="//a[@ui-sref='branch']")
	WebElement branch;

	@FindBy(xpath="//a[@ui-sref='staff']")
	WebElement staff;

	@FindBy(xpath="//span[text()='Entities']/ancestor::a")
	WebElement entities;

	@FindBy(xpath="//input[@name='name']")
	WebElement namefield; 

	@FindBy(xpath="//input[@name='code']")
	WebElement codefield; 

	@FindBy(xpath="//span[text()='Save']/ancestor::button")
	WebElement savebutton; */

	Logger logger = Logger.getLogger(BranchPage.class);

	public void createNewBranch(String name, String code){
		navigateToPage();
		WebElement createbranch = waitForElementVisibility(By.xpath("//span[contains(text(),'Create a new Branch')]/ancestor::button"));
		createbranch.click();
		typeEditBox("name", name);
		typeEditBox("code", code);
		WebElement savebtn = waitForElementToBeClickable(By.xpath("//span[text()='Save']/ancestor::button"));
		savebtn.click();
		Assert.assertNotNull("Failed to Create a branch", waitForElementVisibility(By.xpath("//td[text()='"+name+"']")));
	}

	public boolean searchBranch(String branchname){
		navigateToPage();
		typeEditBox("searchQuery", branchname);
		WebElement searchbranch = waitForElementToBeClickable(By.xpath("//span[text()='Search a Branch']/ancestor::button"));
		if(searchbranch!=null){
			searchbranch.click();
			WebElement branch = waitForElementVisibility(By.xpath("//td[text()='"+branchname+"']"));
			if(branch!=null)
				return true;
		}
		logger.error("Unable to find a Branch with the name:"+branchname);
		return false;
	}

	public boolean deleteBranch(String branchname) {
		navigateToPage();
		searchBranch(branchname);
		WebElement deletebranch = waitForElementToBeClickable(By.xpath("//td[text()='"+branchname+"']/following-sibling::td/button[span[text()='Delete']]"));
		if(deletebranch!=null)
		{
			deletebranch.click();
			if(isTextPresent("Confirm delete operation"))
			{
				driver.findElement(By.xpath("//form[@name='deleteForm']/div/button[span[text()='Delete']]")).click();
				return searchBranch(branchname)?false:true;
			}
			else
			{
				logger.error("Unable to click on delete button");
				return false;
			}
		}
		else
		{
			logger.error("Unable to find branch to delete");
			return false;
		}
	}

	public void editBranch(String branchname) {
		navigateToPage();
		searchBranch(branchname);
		WebElement editbranch = waitForElementToBeClickable(By.xpath("//td[text()='"+branchname+"']/following-sibling::td/button[span[text()='Edit']]"));
		Assert.assertNotNull("Failed to find Staff: "+branchname, editbranch);
		editbranch.click();
		Assert.assertNotNull("Unable to click on edit branch", isTextPresent("Create or edit a Branch"));
		typeEditBox("code", "1234");
		driver.findElement(By.xpath("//button[span[text()='Save']]")).click();
	}


	@Override
	public void assertInPage() {
		Assert.assertNotNull("Unable to navigate to Branches Page", waitForElementVisibility(By.xpath("//h2[text()='Branches']")));
	}

	@Override
	public void navigateToPage() {
		WebElement entities = waitForElementVisibility(By.xpath("//span[text()='Entities']/ancestor::a"));
		if(entities!=null){
			entities.click();
			WebElement branch = waitForElementToBeClickable(By.xpath("//a[@ui-sref='branch']"));
			Assert.assertNotNull("Branch element is not clickable", branch);
			branch.click();
			assertInPage();
		}
		else
			logger.error("Unable to navigate to homepage");
	}
}

