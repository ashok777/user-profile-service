package com.poc.userprofile.service;


import org.springframework.stereotype.Component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;



@Component
public class GoogleGeoCodingServiceProvider implements AddressResolutionProvider {
	
	
	public String getResolvedAddress(String apiKey, String unresolvedAddress) throws Exception {
		
		GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
   		GeocodingResult[] results =  GeocodingApi.geocode(context, unresolvedAddress).await();
   		
   		String resolvedFormattedAddress = results[0].formattedAddress;
		return resolvedFormattedAddress;
	}

}
