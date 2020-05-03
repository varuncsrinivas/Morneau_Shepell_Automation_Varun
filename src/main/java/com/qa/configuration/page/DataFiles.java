package com.qa.configuration.page;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DataFiles {
	public static Properties prop = new Properties();
	
	public static Properties create_RunTime_Data(String key, String value) throws FileNotFoundException, IOException
	{
		prop.setProperty(key, value);
		prop.store(new FileOutputStream("dataprop.properties"), null);
		return prop;
	}

	//This Function will read the properties file at run time.
	public static String get_RunTime_Created_Data(String Key) throws FileNotFoundException 
	{
		FileInputStream ip = new FileInputStream("dataprop.properties");
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String data = prop.getProperty(Key);
		return data;
	}
}
