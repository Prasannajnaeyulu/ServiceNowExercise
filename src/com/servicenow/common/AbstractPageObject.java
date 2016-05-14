package com.servicenow.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import com.servicenow.impl.login.LoginPage;

/**
 * 
 * @author prasannanjaneyulu padavala 
 * This page is the base page for all the page objects. It contains the code to create driver object, To load application environment properties
 * and all the synchronized methods to perform operation on browser like click,type etc..
 *
 */
public abstract class AbstractPageObject {
	protected static WebDriver driver;
	public static Properties envprops;
	static Logger logger = Logger.getLogger(AbstractPageObject.class);
	public static boolean isBrowserOpen = false;
	
	static{
		envprops = new Properties();
		loadprops("environment.properties");
	}

	public static void loadprops(String filename){
		try {
			envprops.load(new FileReader("environment.properties"));
		} catch (FileNotFoundException e) {
			logger.error("environment.properties was not found hence failed to load");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public WebElement waitForElement(By bylocator){
		return waitForElement(bylocator, 30);
	}
	
	public WebElement waitForElementVisibility(By bylocator){
		return waitForElementVisibility(bylocator, 30);
	}
	
	public WebElement waitForElement(By bylocator, int timeunitinseconds){
		WebElement we=null;
		WebDriverWait wait = new WebDriverWait(driver,timeunitinseconds);
		
		try{
			we = wait.until(ExpectedConditions.presenceOfElementLocated(bylocator));
		}
		catch(NoSuchElementException e){
			logger.error("Element not found in the given time"+timeunitinseconds);
		}
		return we;
	}
	
	public WebElement waitForElementVisibility(By bylocator, int timeunitinseconds){
		WebElement we=null;
		WebDriverWait wait = new WebDriverWait(driver,timeunitinseconds);
		
		try{
			we = wait.until(ExpectedConditions.visibilityOfElementLocated(bylocator));
		}
		catch(Exception e){
			logger.error("Element not visible in the given time"+timeunitinseconds);
		}
		return we;
	}
	public WebElement waitForElementToBeClickable(By bylocator, int timeunitinseconds){
		WebElement we=null;
		WebDriverWait wait = new WebDriverWait(driver,timeunitinseconds);
		
		try{
			we = wait.until(ExpectedConditions.elementToBeClickable(bylocator));
		}
		catch(Exception e){
			logger.error("Element not clickable in the given time"+timeunitinseconds);
		}
		return we;
	}
	public WebElement waitForElementToBeClickable(By bylocator){
		return waitForElementToBeClickable(bylocator, 20);	
	}
	
	public void clickLink(String linktext){
		WebElement we = waitForElementVisibility(By.linkText(linktext));
		if(we!=null)
			we.click();
	}
	public void typeEditBox(String editboxidentifier, String texttotype){
		String xpath = "//input[@id='"+editboxidentifier+"' or @name='"+editboxidentifier+"']";
		WebElement editbox = waitForElementVisibility(By.xpath(xpath));
		if(editbox!=null)
		{
			editbox.clear();
			editbox.sendKeys(texttotype);
		}
		else
		{
			logger.error("Failed to find edit box with the xpath: "+xpath);
			return;
		}
	}
	
	public void clickButton(String buttonidentifier){
		String xpath = "//button[@id='"+buttonidentifier+"' or text()='"+buttonidentifier+"']";
		WebElement button = waitForElementVisibility(By.xpath(xpath));
		if(button!=null)
			button.click();
		else
		{
			logger.error("Failed to find button with the xpath: "+xpath);
			return;
		}
	}
	
	public void selectByNameorID(String identifier, String valuetoselect){
		String xpath = "//select[@name='"+identifier+"']";
		WebElement selectelement = waitForElementVisibility(By.xpath(xpath));
		if(selectelement!=null)
		{
			Select select = new Select(selectelement);
			select.selectByVisibleText(valuetoselect);
		}
	}
	
	public boolean isTextPresent(String texttofind){
		int count=0;
		while(!driver.getPageSource().contains(texttofind) && count++<3){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(count>=3){
			return false;
		}
		
		return true;
	}
	
	public void openBrowser(){
		if(!isBrowserOpen){
			String browser = envprops.getProperty("browser");
			if(browser.equalsIgnoreCase("firefox")){
				driver = new FirefoxDriver();
				isBrowserOpen = true;
			}
			else
				if(browser.equalsIgnoreCase("internetexplorer")){
					//specify IEDriverserver.exe path as a second argument
					System.setProperty("webdriver.ie.driver", "");
					driver = new InternetExplorerDriver();
					isBrowserOpen = true;
				}
			else
				if(browser.equalsIgnoreCase("chrome")){
					//specify chromedriver.exe path as a second argument
					System.setProperty("webdriver.chrome.driver", "");
					driver = new ChromeDriver();
					isBrowserOpen = true;
				}
			else
			  logger.error("Invalid Driver Option");	
			
			//driver.get(envprops.getProperty("url"));
			isBrowserOpen = true;
		}
		else
		  logger.info("Browser is already opened");
	}
	
	public static void quit() {
		// TODO Auto-generated method stub
		logger.info("inside quite method...");
		driver.quit();
		isBrowserOpen = false;
		LoginPage.isloggedin=false;
	}
	
	public static void takescreenshot(String filename) throws IOException{
		String outputfile = System.getProperty("user.dir")+"/Screenshots/"+filename;
		logger.info("Taking the screenshot into a file: "+outputfile);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(outputfile));
	}
	
	public abstract void navigateToPage();
	public abstract void assertInPage();
	
}
