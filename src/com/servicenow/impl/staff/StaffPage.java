package com.servicenow.impl.staff;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.servicenow.common.AbstractPageObject;

/**
 * 
 * @author prasannanjaneyulu padavala
 * This page contains implementations for the functionalities one can carry out on Staff Page
 */

public class StaffPage extends AbstractPageObject {
	Logger logger = Logger.getLogger(StaffPage.class);
	
	/*String deletexpath = "//td[text()='abcdef']/following-sibling::td/button[span[text()='Delete']]";
	String editxpath = "//td[text()='abcdef']/following-sibling::td/button[span[text()='Edit']]";
	String viewxpath = "//td[text()='abcdef']/following-sibling::td/button[span[text()='View']]";*/
	
	public void createNewStaff(String name, String branch){
		navigateToPage();
		WebElement createbranch = waitForElementVisibility(By.xpath("//span[contains(text(),'Create a new Staff')]/ancestor::button"));
		createbranch.click();
		typeEditBox("name", name);
		WebElement branchdropdown = waitForElementVisibility(By.name("related_branch"));
		Select select = new Select(branchdropdown);
		select.selectByVisibleText(branch);
		
		WebElement savebtn = waitForElementToBeClickable(By.xpath("//span[text()='Save']/ancestor::button"));
		savebtn.click();
		Assert.assertNotNull("Failed to Create a branch: "+branch, waitForElementVisibility(By.xpath("//td[text()='"+name+"']")));
	}
	
	public boolean searchStaff(String staffname){
		navigateToPage();
		typeEditBox("searchQuery", staffname);
		WebElement searchstaff = waitForElementToBeClickable(By.xpath("//span[text()='Search a Staff']/ancestor::button"));
		searchstaff.click();
		if(waitForElementVisibility(By.xpath("//td[text()='"+staffname+"']"))!=null)
				return true;
		else{
			logger.warn("Failed to find Staff: "+staffname);
			return false;
		}
	}
	
	public boolean deleteStaff(String staffname) {
		navigateToPage();
		
		if(!searchStaff(staffname))
			return false;
		
		WebElement deletestaff = waitForElementToBeClickable(By.xpath("//td[text()='"+staffname+"']/following-sibling::td/button[span[text()='Delete']]"));
		if(deletestaff!=null)
		{
			deletestaff.click();
			if(isTextPresent("Confirm delete operation"))
			{
				driver.findElement(By.xpath("//form[@name='deleteForm']/div/button[span[text()='Delete']]")).click();
				return searchStaff(staffname)?false:true;
			}
			else
			{
				logger.error("Unable to click on delete button");
				return false;
			}
		}
		else{
			logger.error("Unable to find staff to delete");
			return false;
		}
	}
	
	public void editStaff(String staffname) {
		navigateToPage();
		Assert.assertTrue("Failed to find the staff:"+staffname,searchStaff(staffname));
		
		WebElement editstaff = waitForElementToBeClickable(By.xpath("//td[text()='"+staffname+"']/following-sibling::td/button[span[text()='Edit']]"));
		Assert.assertNotNull("Failed to find Staff: "+staffname, editstaff);
		editstaff.click();
		Assert.assertNotNull("Unable to click on edit branch", isTextPresent("Create or edit a Branch"));
		selectByNameorID("related_branch", "abcdef");
		driver.findElement(By.xpath("//button[span[text()='Save']]")).click();
	}


	@Override
	public void assertInPage() {
		Assert.assertNotNull("Unable to navigate to Staffs Page", waitForElementVisibility(By.xpath("//h2[text()='Staffs']")));
	}

	@Override
	public void navigateToPage() {
		WebElement entities = waitForElementVisibility(By.xpath("//span[text()='Entities']/ancestor::a"));
		if(entities!=null){
			entities.click();
			WebElement staff = waitForElementToBeClickable(By.xpath("//a[@ui-sref='staff']"));
			Assert.assertNotNull("Staff link element is not found", staff);
			staff.click();
			assertInPage();
		}
		else
			logger.error("Unable to navigate to homepage");
	}
}
