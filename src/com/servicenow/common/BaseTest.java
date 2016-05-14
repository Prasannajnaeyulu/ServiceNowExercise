package com.servicenow.common;



import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.servicenow.impl.login.LoginPage;
import com.servicenow.util.Xls_Reader;



public class BaseTest {
	public static Logger APP_LOGS=null;
	public static Properties CONFIG=null;
	public static Properties OR=null;
	public static Xls_Reader suiteXls=null;
	public static Xls_Reader suiteAxls=null;
	public static Xls_Reader suiteBxls=null;
	public static Xls_Reader suiteCxls=null;
	public static boolean isInitalized=false;
	
	
	// initializing the Tests
	public void initialize() throws Exception{
		// logs
		if(!isInitalized){
		APP_LOGS = Logger.getLogger(BaseTest.class);
		// config
		APP_LOGS.debug("Loading Property files");
		CONFIG = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"//src//com//securevue//config/config.properties");
		CONFIG.load(ip);
			
		OR = new Properties();
		ip = new FileInputStream(System.getProperty("user.dir")+"//src//com//securevue//config/OR.properties");
		OR.load(ip);
		APP_LOGS.debug("Loaded Property files successfully");
		APP_LOGS.debug("Loading XLS Files");

		// xls file
		/*suiteAxls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//securevue//xls//A suite.xlsx");
		suiteBxls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//securevue//xls//B suite.xlsx");
		suiteCxls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//securevue//xls//C suite.xlsx");
		suiteXls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//securevue//xls//Suite.xlsx");*/
		APP_LOGS.debug("Loaded XLS Files successfully");
		isInitalized=true;
		}
	}
	
	@BeforeMethod
	public void init(){
		new LoginPage().login("admin", "admin");
	}
	
	@AfterMethod
	public void teardown(){
		AbstractPageObject.quit();
	}

}
