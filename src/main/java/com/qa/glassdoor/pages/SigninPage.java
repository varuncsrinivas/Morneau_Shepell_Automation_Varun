package com.qa.glassdoor.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.qa.configuration.page.TestBaseClass;

public final class SigninPage extends TestBaseClass{

	@FindBy(xpath="//*[text()=' Sign In']") 
	private WebElement signInBtn;
	
	@FindBy(xpath="//*[@title='Email Address']") 
	private WebElement emailAddressTextBox;
	
	@FindBy(name="password") 
	private WebElement passwordAddressTextBox;
	
	@FindBy(name="submit") 
	private WebElement submitBtn;


	public SigninPage()
	{
		PageFactory.initElements(driver, this);
	}

	public void go_To_SignInPage()
	{
		click_On_Element(signInBtn);
	}
	
	public boolean signInPage_Validation()
	{
		return isElementDisplayed(signInBtn);
	}
	
	public void enter_login_credentials(String email,String password) {
		wait_For_An_Element_To_Be_Visible(emailAddressTextBox, 5);
		enter_Value_In_Element(emailAddressTextBox, email);
		enter_Value_In_Element(passwordAddressTextBox, password);
	}
	
	public void click_On_Submit_Btn() {
		click_On_Element(submitBtn);
	}

}
