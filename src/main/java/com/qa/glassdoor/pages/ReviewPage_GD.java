package com.qa.glassdoor.pages;


import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.configuration.page.DataFiles;
import com.qa.configuration.page.TestBaseClass;


public final class ReviewPage_GD extends TestBaseClass{

	@FindBy(xpath="//*[@id='EIProductHeaders']/div/a[contains(@class,'reviews')]") 
	private WebElement reviewsLink;	

	@FindBy(id="FilterSorts") 
	private WebElement filterRating;	
	
	@FindBy(xpath="//*[contains(@class,'nextArrow')]") 
	private WebElement nextArrowBtn;
	
	@FindBy(xpath="//*[contains(@class,'gdStars')]/span/span")
	private List<WebElement> reviewsList;
	
	@FindBy(xpath="(//*[@class='hreview'])[1]")
	private WebElement ListFirstRes;
	
	String recommendLoc= "//ancestor::div[2]/div[3]/div/div/span[contains(text(),'Recommend')]";
	String outlookLoc= "//ancestor::div[2]/div[3]/div/div/span[contains(text(),'Outlook')]";
	String ceoLoc= "//ancestor::div[2]/div[3]/div/div/span[contains(text(),'CEO')]";
	String prosLoc="//ancestor::div[2]/div[4]/p[2]";
	String consLoc ="//ancestor::div[2]/div[5]/p[2]";


	public ReviewPage_GD()
	{
		PageFactory.initElements(driver, this);
	}

	String ratingText="";
	String recommendText="";
	String outLookText="";
	String ceoText="";
	String prosText="";
	String consText="";
	
	

	public void click_On_ReviewsLink(){
		wait_For_An_Element_To_Be_Visible(reviewsLink, 10);
		click_On_Element(reviewsLink);
	}	

	public void filter_Rating_By_UserDefined(String rating) throws InterruptedException {
		wait_For_An_Element_To_Be_Visible(filterRating, 10);
		select_Element_By_Visible_Text(filterRating, rating);
		sleep(2000);
	}

	public String get_user_Defined_Rating_Properties(String expRating, String ratingBreakPoint) throws InterruptedException, IOException {
		int count = 0;
		while(true)
		{
			try {
				sleep(1000);
				wait_For_An_Element_To_Be_Visible(ListFirstRes, 5);
			}
			catch(StaleElementReferenceException e) {
				e.printStackTrace();
				sleep(2000);
				//wait_For_An_Element_To_Be_Visible(ListFirstRes, 10);
			}
			wait_For_An_Element_To_Be_Visible(ListFirstRes, 5);
			for (WebElement webElement : reviewsList) {
				String ratingNumber= webElement.getAttribute("title");
				ratingText = ratingNumber;
				if(ratingNumber.equals(expRating))
				{
					List<WebElement> StarRev= driver.findElements(By.xpath("(//*[contains(@class,'gdStars')]/span/span[@title='"+expRating+"'])"));
					for(int i=1;i<=StarRev.size();i++) {
						count = count+1;
						String Locator= "(//*[contains(@class,'gdStars')]/span/span[@title='"+expRating+"'])["+i+"]";
						if(getSizeOfelements(Locator+recommendLoc)!=0) {
							recommendText = element(Locator+recommendLoc).getText();
						}
						else {
							recommendText = "N/A";
						}
						if(getSizeOfelements(Locator+outlookLoc)!=0) {
							outLookText = element(Locator+outlookLoc).getText();
						}
						else {
							outLookText = "N/A";
						}
						if(getSizeOfelements(Locator+ceoLoc)!=0) {
							ceoText = element(Locator+ceoLoc).getText();
						}
						else {
							ceoText = "N/A";
						}

						if(getSizeOfelements(Locator+prosLoc)!=0) {
							prosText = element(Locator+prosLoc).getText();
							prosText = prosText.replaceAll("\\s+"," ");
						}
						else {
							prosText = "N/A";
						}

						if(getSizeOfelements(Locator+consLoc)!=0) {
							consText = element(Locator+consLoc).getText();
							consText = consText.replaceAll("\\s+"," ");
						}
						else {
							consText = "N/A";
						}
						String resultsOf3Star = "Review : "+count+" : Recommended::"+recommendText+" | outLook::"+outLookText+" | CEO: "+ceoText+"| Pros: "+prosText +"| Cons: "+consText;
						System.out.println(resultsOf3Star);	
						DataFiles.create_RunTime_Data("outPutFile","outPutFileForStarRating.txt");
						writeToFile(DataFiles.get_RunTime_Created_Data("outPutFile"),resultsOf3Star);
						
					}
					DataFiles.create_RunTime_Data("countOfReview",""+count);
					break;
				}
			}
			if(ratingText.equals(ratingBreakPoint)) {
				break;
			}
			scroll_To_Element_And_Click(nextArrowBtn);
			
		}
		return ""+count;
	}

}
