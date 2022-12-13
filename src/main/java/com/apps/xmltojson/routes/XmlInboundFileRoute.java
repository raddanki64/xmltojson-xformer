package com.apps.xmltojson.routes;

import	org.apache.camel.spring.SpringRouteBuilder;
import	org.apache.camel.Exchange;
import	org.apache.camel.Processor;
import 	org.springframework.stereotype.Component;
import	org.springframework.beans.factory.annotation.Value;
import	org.springframework.beans.factory.annotation.Autowired;

import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;

import	com.apps.xmltojson.services.XmlToJsonServiceProvider;

@Component
public class XmlInboundFileRoute extends SpringRouteBuilder {
	private static Logger logger = LoggerFactory.getLogger(XmlInboundFileRoute.class);
	
	@Value("${xmlfileurl}")
	private String xmlfileurl;
	
	@Autowired
	private XmlToJsonServiceProvider xmlToJsonServiceProvider;
	
	
	public XmlInboundFileRoute() {
	}
	
	@Override
	public void configure() {
		onException(Exception.class)
		.handled(true)
		.process(new Processor() {
			public void process(Exchange exchange) {
				logger.info("Error in processing");
			}
		})
		.end();
		
		from(xmlfileurl)
		.routeId("XmlFileProcessor")
		.process(new Processor() {
			public void process(Exchange exchange)  {
				String inputFilename = exchange.getIn().toString();
				String outputFilename = "ramesh.json";
				logger.info("Processing inbound file {}", exchange.getIn().toString());
				
				try {
					xmlToJsonServiceProvider.process(inputFilename, outputFilename);
				}
				catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		})
		//.to("bean:handleOrder")
		.end();
	}
	
}
