package com.apps.xmltojson.utils;

import  java.lang.Thread;
import 	java.util.Properties;
import 	java.io.IOException;
import 	java.io.InputStream;

import	org.slf4j.Logger;
import	org.slf4j.LoggerFactory;

//
// state name to state mode mapping utility class
//

public class StateCodeResolver  {
	private static final String STATES_CODES_MAPPING_FILENAME = "states.mappings";
	
	private static final Logger logger = LoggerFactory.getLogger(StateCodeResolver.class);
	
	private static StateCodeResolver instance = null;
	
	private Properties stateNamesToCodesMap = new Properties();
	
	public static StateCodeResolver getInstance() throws Exception  {
		if (instance == null) {
			synchronized (StateCodeResolver .class) {
				if (instance == null) {
					instance = new StateCodeResolver();
				}
			}
		}

        return instance;
	}
	
	public static String getStateCode(String stateName) throws Exception {
	    if((null == stateName) || stateName.isEmpty()) {
	        throw new Exception("Illgegal parameter");
	    }
		
		if(null == instance) {
			instance = getInstance();
		}
		
		stateName = capitalize(stateName);
		return instance.stateNamesToCodesMap.getProperty(stateName);
	}
	
	private static String capitalize(String stateName) 
	{
	    return stateName.substring(0, 1).toUpperCase() + stateName.substring(1);
	}
	
	private StateCodeResolver() throws Exception {
	    ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    try (InputStream resourceStream = loader.getResourceAsStream(STATES_CODES_MAPPING_FILENAME)) {
	    	stateNamesToCodesMap.load(resourceStream);
	    } 
	    catch (IOException ioe) {
	    	logger.error("Unable to load state names to state codes mappings file", ioe);
	    	throw new Exception("Unable to load state names to state codes mappings file");
	    }	
	}
}
