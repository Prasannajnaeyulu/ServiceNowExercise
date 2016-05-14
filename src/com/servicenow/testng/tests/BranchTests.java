package com.servicenow.testng.tests;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.servicenow.common.AbstractPageObject;
import com.servicenow.common.BaseTest;
import com.servicenow.impl.branch.BranchSeleium2Impl;
import com.servicenow.impl.login.LoginPage;/**
 * 
 * @author prasannanjaneyulu padavala
 * This is the class where all Branch Page related tests are defined
 *
 */

public class BranchTests extends BaseTest{
	BranchSeleium2Impl branch=null;

	@BeforeClass
	public void setup(){
		branch = new BranchSeleium2Impl();
		new LoginPage().login("admin", "admin");
		try{
			branch.deleteBranch("Infrastructure");
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			AbstractPageObject.quit();
		}
	}

	@Test(groups="first")
	public void createNewBranch(){
		branch.createNewBranch("Infrastructure", "123");
	}

	@Test(dependsOnGroups="first")
	public void searchBranch(){
		Assert.assertTrue("Failed to find a branch. Please see the screenshot for more details...",branch.searchBranch("Infrastructure"));
	}

	@Test(dependsOnGroups="first")
	public void editBranch(){
		branch.editBranch("Infrastructure");
	}
}
