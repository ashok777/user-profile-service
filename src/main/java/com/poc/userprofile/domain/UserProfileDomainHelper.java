package com.poc.userprofile.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poc.userprofile.model.*;
import com.poc.userprofile.persistence.UserProfileDAO;
import com.poc.userprofile.service.AddressResolutionProvider;

@Component
public class UserProfileDomainHelper {

	@Autowired
	AddressResolutionProvider addressResolutionProvider;
	
	@Autowired
	UserProfileDAO  userProfileDAO;
	
	public List<UserProfile> resolveAddressesAndSave(String apiKey, List<UserProfile> userProfiles) throws Exception{
		
		List<UserProfile> resolvedProfiles = resolveAddresses(apiKey, userProfiles);
		Map<String, List<UserProfile>> userProfilesMap = repackageUserProfiles(resolvedProfiles);
		
		userProfileDAO.persistUserProfiles(userProfilesMap);
		return resolvedProfiles;
		
	}
	public List<UserProfile> lookupUsersForPostalCode(String postalCode) throws Exception{
		
		Map<String, List<UserProfile>> userProfilesMap  = userProfileDAO.readUserProfiles();
		
		List<UserProfile> listOfProfiles = userProfilesMap.get(postalCode);
		if (listOfProfiles == null){
			listOfProfiles = new ArrayList<UserProfile>();
		}
		return listOfProfiles;
	}
	
	private List<UserProfile> resolveAddresses(String apiKey, List<UserProfile> userProfiles) throws Exception{
		
		for (UserProfile userProfile:  userProfiles){
			  String address = userProfile.getAddress();
			  String resolvedReformattedAddress = addressResolutionProvider.getResolvedAddress(apiKey, address);
			  
			  String postalCode = UserProfile.extractPostalCodeFromAddress(resolvedReformattedAddress);
			  
			  //Append postalCode to the original address
			  address = new StringBuilder(address).append(' ').append(postalCode).toString();
			  userProfile.setAddress(address);
		}
		return userProfiles;
	}
	private Map<String, List<UserProfile>> repackageUserProfiles(List<UserProfile> userProfiles){
		
		Map<String, List<UserProfile>> mapOfLists = new HashMap<String, List<UserProfile>>();
		List<UserProfile> listOfProfiles = null;
		
		for (UserProfile userProfile:  userProfiles){
			
			String postalCode = UserProfile.extractPostalCodeFromAddress(userProfile.getAddress());
			
			if (mapOfLists.get(postalCode) != null) {
				listOfProfiles = mapOfLists.get(postalCode);
				listOfProfiles.add(userProfile);
			} else {
				listOfProfiles = new ArrayList<UserProfile>();
				listOfProfiles.add(userProfile);
				mapOfLists.put(postalCode,  listOfProfiles);
			}
		}
		return mapOfLists;
	}
}
