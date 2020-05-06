package com.qa.test;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.qa.configuration.page.DataFiles;
import com.qa.configuration.page.TestBaseClass;
import com.qa.glassdoor.pages.SigninPage;
import com.qa.utility.MonitoringMail;
import com.qa.utility.TestConfig;
import com.qa.glassdoor.pages.GDLandingPage;
import com.qa.glassdoor.pages.ReviewPage_GD;

public final class glassDoorTestCase extends TestBaseClass
{
	SigninPage signIn;
	GDLandingPage landingPage;
	ReviewPage_GD reviewPage;

	@Test(enabled=true )
	private void tc01_Rating_3_Star_Reviews_List_And_Send_Results_Via_Email() throws InterruptedException, IOException, AddressException, MessagingException
	{  
		open_Browser_And_Enter_URL();
		signIn = new SigninPage();
		landingPage = new GDLandingPage();
		MonitoringMail mail = new MonitoringMail();
		reviewPage = new ReviewPage_GD();
		Assert.assertTrue(signIn.signInPage_Validation(),"SignIn Link Not Displayed");
		signIn.go_To_SignInPage();
		signIn.enter_login_credentials(properties.getProperty("username"), properties.getProperty("password"));
		signIn.click_On_Submit_Btn();
		Assert.assertTrue(landingPage.landingPageValidation(),"Not In Landing Page");
		landingPage.enter_value_In_SearchBox_And_Select_Company_From_DropDown(properties.getProperty("companyName"));
		landingPage.click_On_Company_Reviews();
		Assert.assertEquals(landingPage.companyName_Displayed(), properties.getProperty("companyFullName"),"Company Name Not Matches");
		reviewPage.click_On_ReviewsLink();
		reviewPage.filter_Rating_By_UserDefined(properties.getProperty("sortRating"));
		String count= reviewPage.get_user_Defined_Rating_Properties(properties.getProperty("expStarRating"), properties.getProperty("starRatingEndPoint"));
		boolean actual = mail.send_E_Mail(TestConfig.server, TestConfig.from, TestConfig.to, properties.getProperty("subject"), properties.getProperty("messageBody").replace("<count>", count), TestConfig.attachmentPath, TestConfig.attachmentName);
		Assert.assertTrue(actual, "Email Not Sent sucessfully");
	}


	@AfterMethod()
	public void tearDown() throws IOException
	{
		deleteFile(DataFiles.get_RunTime_Created_Data("outPutFile"));
		quitDriver();
	}
}
