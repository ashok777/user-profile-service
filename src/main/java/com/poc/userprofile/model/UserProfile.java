package com.poc.userprofile.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProfile {

    private String name;
    private String  address;

    public static final String POSTAL_CODE_REGEX = "\\d{5}";
    
    public UserProfile() {
    }
    
    public String getName(){
    	return name;
    }
    public void setName(String name){
    	this.name= name;
    }
    public String getAddress(){
    	return address;
    }
    
    public void setAddress(String address){
    	this.address = address;
    }
    public static String  extractPostalCodeFromAddress(String formattedAddress){
		
		Pattern p = Pattern.compile(POSTAL_CODE_REGEX);
		Matcher m = p.matcher(formattedAddress);
		String postalCode="";
		
		while( m.find()) {
			postalCode =  m.group();
		}
		return postalCode;
	}
}
