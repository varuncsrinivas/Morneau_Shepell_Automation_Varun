package com.qa.configuration.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qa.resources.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBaseClass 
{
	public static Properties properties;
	public static FileInputStream fis;
	protected static WebDriver driver;
	public long PageLoadTime=20;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static Logger log = Logger.getLogger("QaLOG");

	public TestBaseClass(){
		try {
			properties = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "\\src\\main\\resources\\properites\\TestData.properties");
			properties.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void open_Browser_And_Enter_URL()
	{
		String browser = properties.getProperty("browser");

		if(browser.equalsIgnoreCase("firefox"))
		{
			log.info("Executing Test in firefox Browser");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
			log.info("Executing Test in Chrome Browser");
			System.setProperty("webdriver.chrome.silentOutput","true");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			if(properties.getProperty("headless").equalsIgnoreCase("y"))
			{
				options.addArguments("--headless");
			}
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-extensions");
			options.addArguments("--start-maximized");
			driver= new ChromeDriver(options);
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(PageLoadTime, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(properties.getProperty("url"));
		log.info("Entered the url: "+properties.getProperty("url"));
	}

	public String getTitle() {
		return driver.getTitle();
	}
	
	public void click_On_Element(WebElement element){
		element.click();
	}

	public void enter_Value_In_Element(WebElement element, String value) {
		element.sendKeys(value);
	}

	public String get_Text_From_An_Element(WebElement element)
	{
		String expText = element.getText();
		return expText;
	}

	public void mouse_Hover_To_Element(WebElement element)
	{
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
	}

	public void mouse_Hover_To_Element_And_Perform_Click(WebElement element)
	{
		Actions act = new Actions(driver);
		act.moveToElement(element).click().build().perform();
	}

	public void wait_For_An_Element_To_Be_Visible(WebElement element, int seconds)
	{
		WebDriverWait wait= new WebDriverWait(driver, seconds);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		catch (Exception e) {
			wait.until(ExpectedConditions.visibilityOf(element));
		}
	}

	public void scroll_To_Element_And_Click(WebElement element)
	{
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void switch_To_Frame_By_Index_Number(int index)
	{
		driver.switchTo().frame(index);
	}

	public void come_Out_Of_Frame()
	{
		driver.switchTo().defaultContent();
	}

	public WebElement element(String locator) {
		return driver.findElement(By.xpath(locator));
	}

	public int getSizeOfelements(String locator) {
		return driver.findElements(By.xpath(locator)).size();
	}

	public void select_Element_By_Visible_Text(WebElement element,String string)
	{
		try {
			Select selectsize = new Select(element);
			selectsize.selectByVisibleText(string);
		}
		catch (StaleElementReferenceException e) {
			Select selectsize = new Select(element);
			selectsize.selectByVisibleText(string);
		}
		
	}

	public boolean isElementDisplayed(WebElement element) {
		boolean res = true;
		try
		{
			if(element.isDisplayed()){
				res= true;
			};
		}
		catch (Exception e) {
			res= false;
		}
		return res;
	}

	public void sleep(long seconds)
	{
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void extent_Report_Log(WebElement element, String message)
	{
		if(properties.getProperty("Maven_RunMode").equalsIgnoreCase("y"))
		{
			test.log(LogStatus.INFO, message + "--"+element);
		}
		else
		{
			System.out.println("maven Log not captured as running the test in debug mode");
		}
	}

	public void extent_Log(String message)
	{
		if(properties.getProperty("Maven_RunMode").equalsIgnoreCase("y"))
		{
			test.log(LogStatus.INFO, message);
		}
		else
		{
			System.out.println("maven Log not captured as running the test in debug mode");
		}
	}

	public void quitDriver()
	{
		if(driver!=null)
		{
			driver.quit();
		}
	}

	public void writeToFile(String FileName, String str) throws IOException {
		File file = new File(FileName);
		FileWriter fw = new FileWriter(file, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(str);
		pw.close();
	}
	
	public void deleteFile(String str) throws IOException {
		File file = new File(str);
		file.delete();
	}
	
}

