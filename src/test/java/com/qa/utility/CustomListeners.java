package com.qa.utility;
import java.io.IOException;
import java.util.Date;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.qa.configuration.page.TestBaseClass;
import com.qa.resources.ScreenShotUtility;
import com.relevantcodes.extentreports.LogStatus;

public final class CustomListeners extends TestBaseClass implements ITestListener,ISuiteListener {

	public void onFinish(ITestContext arg0) {
		Date d = new Date();
		log.info("-----------------------------------------------------------------------------------------------");
		log.info(arg0.getName().toUpperCase()+ "::Test Suite End ::"+":: Time ::"+d.getHours()+d.getMinutes());
		log.info("-----------------------------------------------------------------------------------------------");
	}

	public void onStart(ITestContext arg0) {
		Date d = new Date();
		log.info("------------------------------------------------------------------------------------------------");
		log.info(arg0.getName().toUpperCase()+ ":: Test Suite Begin ::"+":: Time ::"+d.getHours()+d.getMinutes());
		log.info("------------------------------------------------------------------------------------------------");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		log.info("-------------------------------------------------------------------------------------------------");
		log.info(arg0.getName().toUpperCase()+ " : onTestFailedButWithinSuccessPercentage");
		log.info("-------------------------------------------------------------------------------------------------");
	}

	public void onTestFailure(ITestResult arg0) {

		System.setProperty("org.uncommons.reportng.escape-output","false");
		try {
			ScreenShotUtility.captureScreenshot();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info(LogStatus.FAIL+arg0.getName().toUpperCase());
		test.log(LogStatus.FAIL, arg0.getName().toUpperCase()+" Failed with exception : "+arg0.getThrowable());
		test.log(LogStatus.INFO, test.addScreenCapture(ScreenShotUtility.screenshotName));
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href="+ScreenShotUtility.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+ScreenShotUtility.screenshotName+"><img src="+ScreenShotUtility.screenshotName+" height=200 width=200></img></a>");
		rep.endTest(test);
		rep.flush();
	}

	public void onTestSkipped(ITestResult arg0) {
		log.info(LogStatus.SKIP+arg0.getName().toUpperCase());
		test.log(LogStatus.SKIP, arg0.getName().toUpperCase()+" ::Skipped the test as the Run mode is NO");
		rep.endTest(test);
		rep.flush();
	}


	public void onTestStart(ITestResult arg0) {
		test = rep.startTest(arg0.getName().toUpperCase());
		log.info("**********************************************************************************************");
		log.info("Test case started : "+arg0.getName().toUpperCase());
		log.info("**********************************************************************************************");
	}

	public void onTestSuccess(ITestResult arg0) {

		log.info("**********************************************************************************************");
		log.info(LogStatus.PASS+arg0.getName().toUpperCase()+ " : PASS");
		log.info("**********************************************************************************************");
		test.log(LogStatus.PASS, arg0.getName().toUpperCase()+" : PASS");
		rep.endTest(test);
		rep.flush();

	}

	public void onFinish(ISuite arg0) {

	}

	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub

	}

}
