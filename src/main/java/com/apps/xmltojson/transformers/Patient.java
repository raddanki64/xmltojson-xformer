package com.apps.xmltojson.transformers;

import	com.apps.xmltojson.utils.GenderNameResolver;
import	com.apps.xmltojson.utils.StateCodeResolver;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

import com.fasterxml.jackson.annotation.JsonProperty;

import	org.slf4j.Logger;
import	org.slf4j.LoggerFactory;

// pojo for "patient" element

@XmlAccessorType(XmlAccessType.FIELD)
public class Patient
{
	private static final Logger logger = LoggerFactory.getLogger(Patient.class);
	
	private static DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	private static LocalDate curDate = LocalDate.now();
		
    private static Integer obtainAge(LocalDate dob)   
    {  
    	return new Integer(Period.between(dob, curDate).getYears());
    }  
	
    @XmlElement(name = "id")
    private Integer patientid;
    
    @XmlElement(name = "gender")
    private String sex;
    
    @XmlElement(name = "state")
    private String state;
    
    @XmlElement(name = "name")
    private String name;
    
    @XmlElement(name = "dob")
    private String dob;
    
    private Integer age;  
    
    public Patient() {
    }
    
    public Integer getPatientid()
    {
        return patientid;
    }
    
    public String getSex()
    {    	
    	String genderName = null;
    	
    	try {
    		genderName = GenderNameResolver.getGenderName(sex);
    	}
    	catch(Exception e)
    	{
    		logger.error("Unable to resolve gender name");
    	}
    	
    	return genderName;
    }

    public void setSex(String sex)
    {
    	this.sex = sex;
    }
    
    public String getName( ) {
    	return name;
    }
    
    public String getState()
    {
    	String stateCode = null;
    	
    	try {
    		stateCode = StateCodeResolver.getStateCode(state);
    	}
    	catch(Exception e)
    	{
    		logger.error("Unable to resolve state code");
    	}
    	
    	return stateCode;
    } 
    
    public Integer getAge() {
    	LocalDate dobAsLocalDate = LocalDate.parse(dob, dobFormatter);  
    	return obtainAge(dobAsLocalDate);
    }
}