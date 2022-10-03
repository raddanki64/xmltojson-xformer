package com.apps.xmltojson.services;

import  com.apps.xmltojson.services.XmlToJsonService;

import 	org.junit.Before;
import 	org.junit.Rule;
import 	org.junit.Test;
import  org.junit.runner.RunWith;
import 	org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;

import	org.apache.commons.io.FileUtils;

import	java.io.File;
import	java.io.IOException;
import 	java.nio.file.Files;
import 	java.nio.file.Paths;
import	java.nio.charset.Charset;

import	org.slf4j.Logger;
import	org.slf4j.LoggerFactory;

// Unit test steps
//		Create input and output temporary files
//		Write known xml to the input file
//		Using service, generate json
//		Read output json from the generated file
//		Compare expected json to generated json
//

public class XmlToJsonServiceProviderTest {
	private static String INPUT_FILENAME = "testdata.xml";
	private static String OUTPUT_FILENAME = "testdata.json";
	private static String CHAR_SET_NAME = "UTF-8";
	
	private static final Logger logger = LoggerFactory.getLogger(XmlToJsonServiceProviderTest.class);
			
	private static String inboundXml = ""
			+ "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<patients>"
			+ "<patient>"
			+ "<id>1234</id>"
			+ "<gender>m</gender>"
			+ "<name>John Smith</name>"
			+ "<state>Michigan</state>"
			+ "<dob>03/04/1962</dob>"
			+ "</patient>"  
			+ "<patient>"
			+ "<id>1235</id>"
			+ "<gender>f</gender>"
			+ "<name>Jane Smith</name>"
			+ "<state>Ohio</state>"
			+ "<dob>08/24/1971</dob>"
			+ "</patient>"
			+ "</patients>"
			;
	
	private static String expectedJson = "{\"patients\":[{\"patientid\":1234,\"sex\":\"male\",\"state\":\"MI\",\"name\":\"John Smith\",\"age\":60},{\"patientid\":1235,\"sex\":\"female\",\"state\":\"OH\",\"name\":\"Jane Smith\",\"age\":51}]}";

	private static XmlToJsonService instanceOfTransformer = XmlToJsonServiceProvider.getInstance();
	
	private File inputXmlFile;
	private File outputJsonFile;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    @Before
    public void setUp() {
        try {
        	inputXmlFile = folder.newFile(INPUT_FILENAME);
            FileUtils.writeStringToFile(inputXmlFile, inboundXml, Charset.forName(CHAR_SET_NAME));
            outputJsonFile = folder.newFile(OUTPUT_FILENAME);
        }
        catch(IOException ioe ) {
        	logger.error("Error creating temporary file(s) in {}", this.getClass().getSimpleName());
        }
    }
	
	@Test
	public void testXmlToJsonConversion() {
		try {
			instanceOfTransformer.process(inputXmlFile.getAbsolutePath(), outputJsonFile.getAbsolutePath());
			String generatedJson = new String(Files.readAllBytes(Paths.get(outputJsonFile.getAbsolutePath())));
			assertEquals(expectedJson, generatedJson);
		}
		catch(Exception e) {
			logger.error("Error processing input file {}", inputXmlFile.getAbsolutePath());
		}
		
		logger.info("Completed processing, generated file {}", outputJsonFile.getAbsolutePath());
	}
}
