package com.apps.xmltojson.services;

import	com.apps.xmltojson.transformers.Patients;

import	org.springframework.stereotype.Service;

import 	javax.xml.bind.JAXB;
import  java.lang.Thread;
import 	java.io.File;
import  java.io.FileOutputStream;

import 	com.fasterxml.jackson.databind.ObjectMapper;

import	org.slf4j.Logger;
import	org.slf4j.LoggerFactory;

//
//	provider can be instantiated using static method or through properties file
//  driver user properties file to instantiate the provider
//

@Service
public class XmlToJsonServiceProvider implements XmlToJsonService {
	private static final Logger logger = LoggerFactory.getLogger(XmlToJsonServiceProvider.class);
	
	private static XmlToJsonService instance = null;
	
	public static XmlToJsonService getInstance() {
        if (instance == null) {
            synchronized (XmlToJsonServiceProvider .class) {
                if (instance == null) {
                    instance = new XmlToJsonServiceProvider();
                }
            }
        }
        
        return instance;
	}
	
	public XmlToJsonServiceProvider() {
	}
	
	public void process(String inputFile, String outputFile) throws Exception {
		logger.debug("Entered {}", Thread.currentThread().getStackTrace()[1].getMethodName());
		
		if((null == inputFile) || inputFile.length() <= 0) {
			throw new Exception("Invalid input file");
		}
		
		if((null == outputFile) || outputFile.length() <= 0) {
			throw new Exception("Invalid outputFile file");
		}		
		
		logger.info("Convert xml to json processor starting, input file = {}, output file = {}", inputFile, outputFile);
		
		Patients patients = JAXB.unmarshal(new File(inputFile), Patients.class);
		
		logger.debug("Input file records = {}", patients);
		
		ObjectMapper mapper = new ObjectMapper();
		FileOutputStream fos = new FileOutputStream(outputFile);
		mapper.writeValue(fos, patients);
		
		fos.flush();
		fos.close();
		
		logger.info("Converted xml to json, and output saved to file {}", outputFile);
		logger.debug("Exiting {}", Thread.currentThread().getStackTrace()[1].getMethodName());
	}
}


