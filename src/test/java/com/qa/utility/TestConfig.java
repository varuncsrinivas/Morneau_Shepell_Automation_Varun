package com.qa.utility;

import com.qa.configuration.page.DataFiles;
import com.qa.configuration.page.TestBaseClass;

public class TestConfig extends TestBaseClass{
	
	public static String server="smtp.gmail.com";
	public static String from = properties.getProperty("username");
	public static String password = properties.getProperty("password");
	public static String[] to =properties.getProperty("emailTo").split(",");
	public static String attachmentPath=System.getProperty("user.dir")+"//"+DataFiles.prop.getProperty("outPutFile");
	public static String attachmentName=DataFiles.prop.getProperty("outPutFile");

}
