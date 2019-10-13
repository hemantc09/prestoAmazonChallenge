package com.presto.utilities;

import org.openqa.selenium.WebDriver;

public class Utilities {
	public static String path = null;
	
	public static void setDriver(WebDriver driver) {
		
		String path = System.getProperty("user.dir");
		System.out.println(path);
		System.setProperty("webdriver.gecko.driver", path+"/lib/firefoxDriver/geckodriver");
	}

}
