package fr.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesLoader {
	private static volatile PropertiesLoader propertiesLoader;
	private static final String fileName = "todolist.properties";
	private Properties prop;
	private Logger logger = Logger.getRootLogger();
	private String DBName;
	private String ACCOUNT_SID;
	private String AUTH_TOKEN;
	private String FromNumber;
	private String ToNumber;
	
	private PropertiesLoader() {
		try {
			InputStream inputStream  = getClass().getClassLoader().getResourceAsStream(fileName);
			prop = new Properties();
			prop.load(inputStream);
			DBName = prop.getProperty("DBName");
			ACCOUNT_SID = prop.getProperty("ACCOUNT_SID");
			AUTH_TOKEN = prop.getProperty("AUTH_TOKEN");
			FromNumber = prop.getProperty("FromNumber");
			ToNumber = prop.getProperty("ToNumber");
			
		} catch (IOException e) {
			logger.error("Error while loading properties file "+fileName, e);
		}
	}
	
	public static PropertiesLoader getInstance(){
		synchronized(PropertiesLoader.class){
			if(propertiesLoader==null){
				propertiesLoader = new PropertiesLoader();
			}
		}
		return propertiesLoader;
	}

	public String getDBName() {
		return DBName;
	}
	
	public String getAccountSID(){
		return this.ACCOUNT_SID;
	}
	
	public String getAuthToken(){
		return this.AUTH_TOKEN;
	}

	public String getFromNumber() {
		return FromNumber;
	}

	public String getToNumber() {
		return ToNumber;
	}

}