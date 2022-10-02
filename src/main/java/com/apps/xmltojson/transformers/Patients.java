package com.apps.xmltojson.transformers;

import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

// pojo for "patients" element

@XmlAccessorType(XmlAccessType.FIELD)
public class Patients
{
    private List<Patient> patient = new ArrayList<Patient>();

    public Patients() {
    }

    public List<Patient> getPatients()
    {
        return patient;
    }
    
    public String toString() {
    	return "Size: " + patient.size();
    }
}