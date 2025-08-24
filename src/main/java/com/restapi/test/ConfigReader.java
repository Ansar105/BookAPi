package com.restapi.test;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {

	
	private static Properties props = new Properties();

    static {
        try {
        	 String userDirectory = System.getProperty("user.dir");
        	 System.out.println(userDirectory);
        	 

            FileInputStream fis = new FileInputStream("C:\\Users\\7119\\ETLWorkspace\\RESTAPIPOC\\src\\test\\java\\Resource\\config.properties");
            props.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
