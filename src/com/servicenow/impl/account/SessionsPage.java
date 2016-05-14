package com.servicenow.impl.account;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.servicenow.common.AbstractPageObject;
import com.servicenow.impl.login.LoginPage;
import com.servicenow.pojo.Sessions;
import static com.servicenow.common.TableUtil.*;

public class SessionsPage extends AbstractPageObject{

	Logger logger = Logger.getLogger(SessionsPage.class);

	public List<Sessions> getActiveSessions(String username) {
		if(!LoginPage.loggedinuser.equalsIgnoreCase(username))
		{
			logger.error("Currently logged in user: "+LoginPage.loggedinuser+" is different from the expected user: "+username);
			return null;
		}
		navigateToPage();
		int rows = getTableRowsCount("table-striped");
		/*int cols = getTableColumnsCount("table-striped");
		for(int i=0;i<rows;i++)
		{
			Sessions s = new Sessions();
			for(int j=0;j<cols;j++){
				s.set
			}
		}*/
		List<Sessions> sessions = new ArrayList<Sessions>();
		for(int i=1;i<=rows;i++){
			Sessions s = new Sessions();
			s.setIpaddress(getTableCellData("table-striped", i, 1));
			s.setUseragent(getTableCellData("table-striped", i, 2));
			s.setDate(getTableCellData("table-striped", i, 3));
			sessions.add(s);
		}

		return sessions;
	}

	public boolean invalidateSession(Sessions session) {
		navigateToPage();
		String ipaddress = (session.getIpaddress()!=null && !session.getIpaddress().trim().equals(""))?session.getIpaddress():"";
		String useragent = (session.getUseragent()!=null && !session.getUseragent().trim().equals(""))?session.getUseragent():"";
		String date = (session.getDate()!=null && !session.getDate().trim().equals(""))?session.getDate():"";

		StringBuilder xpath=new StringBuilder("//tr[");
		//This is the complete xpath if all properties of session object are set
		//tr[descendant::td[contains(text(),'Mozilla')] and descendant::td[contains(text(),'0:0:')] and descendant::td[contains(text(),'11 May 2016')]]/td[4]/button

		//This code is to form xpath dynamically to identify invalidate button of a particular session
		if(!ipaddress.isEmpty())
			xpath.append("descendant::td[contains(text(),'"+ipaddress+"')]");

		if(!useragent.isEmpty() && !ipaddress.isEmpty())
			xpath.append(" and descendant::td[contains(text(),'"+useragent+"'");
		else if(!useragent.isEmpty() && ipaddress.isEmpty())
			xpath.append("descendant::td[contains(text(),'"+useragent+"'");

		if(!date.isEmpty() && (!ipaddress.isEmpty() || !useragent.isEmpty()))
			xpath.append(" and descendant::td[contains(text(),'"+date+"')]");
		else if(!date.isEmpty())
			xpath.append("descendant::td[contains(text(),'"+date+"'");

		xpath.append("]/td[4]/button");

		logger.info("xpath for invalidate table button:" + xpath.toString());
		//get all sessions which are matched with the session criteria and invalidate them all
		int failcount=0;
		List<WebElement> lssessions = driver.findElements(By.xpath(xpath.toString())); 
		for(int i=0;i<lssessions.size();i++){
			WebElement invalidatebutton = waitForElementToBeClickable(By.xpath(xpath.toString()));
			if(invalidatebutton!=null)
			   invalidatebutton.click();
			else{
				failcount++;
				logger.error("Couldn't able to find invalidate button for session"+session.getIpaddress()+","+session.getUseragent());
			}

			if(isTextPresent("Session invalidated!")){
				logger.info("successfully invalidated the session.");
			}

			else if(isTextPresent("The session could not be invalidated."))
			{
				logger.error("Failed to invalidate the session. Please see the screenshot for more details");
				return false;
			}
		}
		if(lssessions.isEmpty())
		{
			logger.error("Couldn't able to find invalidate button for session"+session.getIpaddress()+","+session.getUseragent());
			return false;
		}
		return failcount==0?true:false;
	}

	public boolean logout(){
		if(isTextPresent("Entities") && isTextPresent("Account"))
		{
			WebElement account = waitForElementVisibility(By.xpath("//span[text()='Account']/ancestor::a"));
			Assert.assertNotNull("Account menu doesn't exist", account);
			account.click();
			WebElement logout = waitForElementToBeClickable(By.xpath("//span[text()='Log out']/ancestor::a"));
			Assert.assertNotNull("logout element under Account menu doesn't exist", logout);
			logout.click();
			Assert.assertNotNull(waitForElementVisibility(By.xpath("//h1[text()='Welcome to Gurukula!']")));
			return true;
		}
		else
		{
			logger.error("Seems to be no one logged into the application");
			return false;
		}

	}


	@Override
	public void assertInPage() {
		if(waitForElementVisibility(By.xpath("//h2[starts-with(text(),'Active sessions for')]"))!=null)
			return;
		else
			logger.error("Unable to navigate to Sessions Page");
	}

	@Override
	public void navigateToPage() {
		WebElement account = waitForElementVisibility(By.xpath("//span[text()='Account']/ancestor::a"));
		if(account!=null){
			account.click();
			WebElement sessions = waitForElementToBeClickable(By.xpath("//a[@ui-sref='sessions']"));
			Assert.assertNotNull("Sessions element under Account menu doesn't exist", sessions);
			sessions.click();
			assertInPage();
		}
		else
			logger.error("Unable to navigate to homepage");

	}

}
