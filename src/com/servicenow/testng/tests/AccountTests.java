package com.servicenow.testng.tests;

import java.util.List;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.servicenow.common.BaseTest;
import com.servicenow.impl.account.AccountSelenium2Impl;
import com.servicenow.pojo.Sessions;
/**
 * 
 * @author prasannanjaneyulu padavala
 * This is the class where all Accunt Menu tests are defined like, Sessions, Password, Settings etc...
 *
 */
public class AccountTests extends BaseTest{
	Logger logger = Logger.getLogger(AccountTests.class);
	AccountSelenium2Impl accounts;
	
	@BeforeClass
	public void setup(){
		accounts = new AccountSelenium2Impl();
	}
	
	@Test
	public void changePassword() {	
		boolean b = accounts.changePassword("admin");
		Assert.assertEquals(b, true, "Unable to change Password. Please see the screenshot");
	}

	@Test
	public void changeSettings() {
		
		boolean b = accounts.changeSettings("admin", "admin", "admin@gurukul.com", "English");
		Assert.assertEquals(b, true, "Unable to change settings. Please see the screenshot");
	}

	@Test
	public void findActiveSession() {
		boolean flag=false;
		
		List<Sessions> ls = accounts.getActiveSessions("admin");
		for(Sessions s:ls){
			if(s.getDate().equalsIgnoreCase("14 MAY 2016") && s.getUseragent().contains("Mozilla") &&
					s.getIpaddress().contains("0:0:0:0:0:0:0:1"))
			{
				logger.info("Session is found in users active sessions list. Please see the screenshot");
				flag = true;
				break;
			}
		}
		Assert.assertEquals(flag, true, "Unable to find active session. Please see the screenshot");
	}
	
	@Test
	public void invalidateSession() {
		Sessions session = new Sessions();
		session.setIpaddress("0:0:0:0:0:0:0:1");
		session.setDate("14 May 2016");
		boolean b = accounts.invalidateSession(session);
		Assert.assertEquals(b, true, "Unable to invalidate session. Please see the screenshot");
	}

	@Test
	public void logout() {
		boolean b = accounts.logout();
		Assert.assertEquals(b, true, "Unable to logout. Please see the screenshot");
	}
}
