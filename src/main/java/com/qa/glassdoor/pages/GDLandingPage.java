package com.qa.glassdoor.pages;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.qa.configuration.page.TestBaseClass;

public final class GDLandingPage extends TestBaseClass{

	@FindBy(id="sc.keyword") 
	private WebElement searchInputBox;
	
	@FindBy(xpath="//*[@data-header='companies']") 
	private WebElement companiesDropDown;
	
	@FindBy(xpath="//*[@class='menu-nav']/li[@class='reviews']") 
	private WebElement companyReviewsLink;
	
	@FindBy(id="DivisionsDropdownComponent")
	private WebElement companyName;	
	
	public GDLandingPage()
	{
		PageFactory.initElements(driver, this);
	}

	public boolean landingPageValidation() {
		wait_For_An_Element_To_Be_Visible(searchInputBox, 10);
		return isElementDisplayed(searchInputBox);
	}
	
	public void enter_value_In_SearchBox_And_Select_Company_From_DropDown(String companyName)
	{
		enter_Value_In_Element(searchInputBox, companyName);
		wait_For_An_Element_To_Be_Visible(companiesDropDown, 5);
		click_On_Element(companiesDropDown);
		searchInputBox.sendKeys(Keys.ENTER);
		searchInputBox.sendKeys(Keys.ESCAPE);
	}
	
	public void click_On_Company_Reviews() {
		wait_For_An_Element_To_Be_Visible(companyReviewsLink, 10);
		click_On_Element(companyReviewsLink);
	}
	
	public String companyName_Displayed()
	{
		sleep(1000);
		wait_For_An_Element_To_Be_Visible(companyName, 10);
		return get_Text_From_An_Element(companyName);
	}
}
