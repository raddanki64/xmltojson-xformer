package com.apps.xmltojson;

import  com.apps.xmltojson.services.XmlToJsonService;

import	org.slf4j.Logger;
import	org.slf4j.LoggerFactory;

import	java.util.Properties;
import 	java.io.InputStream;
import  java.io.IOException;

// 
// driver class
//

public class XmlToJsonTransformer {
	private static final String PROPERTIES_FILENAME = "application.properties";
	private static final String PROPERTIES_XFORMER_NAME = "xmltojson-transformer-impl";
	
	private static final Logger logger = LoggerFactory.getLogger(XmlToJsonTransformer.class);
	
	public static void main(String[] args) {
		XmlToJsonService provider = null;
		
		// validate inbound parameters
		if(args.length != 2) {
			System.err.println("Please pass in input xml file name and output json file names.");
			System.exit(-1);
		}
		
		logger.debug("Entered {}", Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// load application properties
		Properties properties = new Properties();
		
	    ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    try (InputStream resourceStream = loader.getResourceAsStream(PROPERTIES_FILENAME)) {
	    	properties.load(resourceStream);
	    } 
	    catch (IOException ioe) {
	    	logger.error("Unable to load application properties, sorry!", ioe);
	    	System.exit(-1);
	    }	      
	      
	    // get transformer class name from defined properties
	    String transformerClassName = properties.getProperty(PROPERTIES_XFORMER_NAME);
	    
	    // instantiate xml to json transformer
	    try {
	    	provider = (XmlToJsonService) Class.forName(transformerClassName).newInstance();
	    }
	    catch(Exception e) {
	    	logger.error("Unable to instantiate the transformer class, sorry!", e);
	    	System.exit(-1);
	    }
		
	    // validate provider's presense
		if(null == provider) {
			logger.error("Inconsistent application state detected, thus failing!");
			System.exit(-1);
		}
	    
		// invoke transformer with input and output file names
		try {
			provider.process(args[0], args[1]);
		}
		catch(Exception e) {
			logger.error("Xml to json converstaion failed", e);
		}
		
		// epilog
		logger.debug("Exiting {}", Thread.currentThread().getStackTrace()[1].getMethodName());
	}
}


