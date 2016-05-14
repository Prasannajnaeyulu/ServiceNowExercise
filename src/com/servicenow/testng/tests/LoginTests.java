package com.servicenow.testng.tests;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.servicenow.common.AbstractPageObject;
import com.servicenow.impl.login.LoginSelenium2Impl;
/**
 *11 
 * @author prasannanjaneyulu padavala 
 * This is the class where all Login and registration Page related tests are defined
 */
public class LoginTests {
	LoginSelenium2Impl login=null;
	
	@AfterMethod
	public void teardown(){
		AbstractPageObject.quit();
	}
	
	@BeforeClass
	public void setup(){
		login = new LoginSelenium2Impl();
	}
	
	@Test
	public void registernewuser(){
		boolean b = login.registerNewAccount("user1", "user1@abc.com", "user1");
		Assert.assertEquals(b, true, "failed to register(create) new user.Please see the screenshot for more details.");
	}
	
	@Test
	public void invalidLogin(){
		boolean b = login.invalidLogin("admin", "admin123");
		Assert.assertEquals(b, true, "login was not failed. please see the screenshot for more details.");
	}
	//dependsOnMethods={"registernewuser,invalidlogin"}
	@Test
	public void login(){
		boolean b = login.login("admin", "admin");
		Assert.assertEquals(b, true, "Unable to login please see the screenshot for more details.");
	}
}
