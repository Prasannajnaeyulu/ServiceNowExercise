package com.servicenow.testng.tests;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.servicenow.common.AbstractPageObject;
import com.servicenow.common.BaseTest;
import com.servicenow.impl.branch.BranchSeleium2Impl;
import com.servicenow.impl.login.LoginPage;
import com.servicenow.impl.staff.StaffSelenium2Impl;
/**
 * 
 * @author prasannanjaneyulu padavala
 * This is the class where all Staff Page related tests are defined
 *
 */
public class StaffTests extends BaseTest{
	StaffSelenium2Impl staff=null;

	@BeforeClass
	public void setup(){
		staff = new StaffSelenium2Impl();
		new LoginPage().login("admin", "admin");
		try{
			staff.deleteStaff("Adam");
		}
		catch (Exception e) {
		}
		finally{
			AbstractPageObject.quit();
		}
	}

	@Test(groups="first")
	public void createNewStaff(){
		staff.createNewStaff("Adam","Infrastructure");
	}

	@Test(dependsOnGroups="first")
	public void searchStaff(){
		Assert.assertTrue("Unable to find the staff. Please see the screenshot for more details..", staff.searchStaff("Adam"));
	}

	@Test(dependsOnGroups="first")
	public void editStaff(){
		staff.editStaff("Adam");
	}

	@Test(groups="second",dependsOnGroups="first")
	public void deleteStaff(){
		Assert.assertTrue("Unable to delete the staff. Please see the screenshot for more details..", staff.deleteStaff("Adam"));
	}
	
	//Delete branch is failing if the branch is referred in the staff. Also, this branch is required during Staff creation hence calling it at this place 
	@Test(dependsOnGroups={"first","second"})
	public void deleteBranch(){
		Assert.assertTrue("Unable to delete the staff. Please see the screenshot for more details..", new BranchSeleium2Impl().deleteBranch("Infrastructure"));
	}
}
