package com.servicenow.common.listeners;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyTestListener implements ITestListener{
	
	public static int failurecount;
	Logger logger = Logger.getLogger(MyTestListener.class);
	
	public MyTestListener() {
		failurecount = 0;
	}

	@Override
	public void onFinish(ITestContext context) {

	}

	@Override
	public void onStart(ITestContext context) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		failurecount = 0;		
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		if(failurecount > 0){
			logger.error("As the test contains verification failures, Setting the end result to FAIL. Please see the log for verification" +
			"failures");
			result.setStatus(ITestResult.FAILURE);
		}
		
	}
	
}
