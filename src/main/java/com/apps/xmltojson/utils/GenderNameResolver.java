package com.apps.xmltojson.utils;

import  java.lang.Thread;
import 	java.util.Properties;
import 	java.io.IOException;
import 	java.io.InputStream;

import	org.slf4j.Logger;
import	org.slf4j.LoggerFactory;

//
// gender code to gender name mapping utility class
//

public class GenderNameResolver  {
	private static final String GENDER_NAMES_MAPPING_FILENAME = "genders.mappings";
	
	private static final Logger logger = LoggerFactory.getLogger(GenderNameResolver.class);
	
	private static GenderNameResolver instance = null;
	
	private Properties genderCodesToNamesMap = new Properties();
	
	public static GenderNameResolver getInstance() throws Exception  {
		if (instance == null) {
			synchronized (GenderNameResolver .class) {
				if (instance == null) {
					instance = new GenderNameResolver();
				}
			}
		}

        return instance;
	}
	
	public static String getGenderName(String genderCode) throws Exception {
	    if((null == genderCode) || genderCode.isEmpty()) {
	        throw new Exception("Illgegal parameter");
	    }
		
		if(null == instance) {
			instance = getInstance();
		}
		
		return instance.genderCodesToNamesMap.getProperty(genderCode);
	}
	
	private GenderNameResolver() throws Exception {
	    ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    try (InputStream resourceStream = loader.getResourceAsStream(GENDER_NAMES_MAPPING_FILENAME)) {
	    	genderCodesToNamesMap.load(resourceStream);
	    } 
	    catch (IOException ioe) {
	    	logger.error("Unable to load gender codes to gender names mappings file", ioe);
	    	throw new Exception("Unable to load gender codes to gender names mappings file");
	    }	
	}
}
