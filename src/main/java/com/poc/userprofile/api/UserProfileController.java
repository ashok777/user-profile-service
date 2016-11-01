package com.poc.userprofile.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.userprofile.domain.UserProfileDomainHelper;
import com.poc.userprofile.model.UserProfile;

@RestController
public class UserProfileController {

	@Autowired
    private UserProfileDomainHelper userProfileDomainHelper;
    
    @RequestMapping("/userprofiles")
    public ResponseEntity<List<UserProfile>> getUsers(@RequestParam(value="postalCode") String postalCode) throws Exception {
    	
    	List<UserProfile> userProfiles =  userProfileDomainHelper.lookupUsersForPostalCode(postalCode);
        return new ResponseEntity<List<UserProfile>>(userProfiles, HttpStatus.OK);
    }
    
    @RequestMapping(value="/userprofiles", method=RequestMethod.POST)
   	public ResponseEntity<List<UserProfile>> saveProfiles(@RequestParam(value="apiKey") String apiKey,
   			                                              @RequestBody List<UserProfile> userProfiles) throws Exception{
     		
    	userProfiles = userProfileDomainHelper.resolveAddressesAndSave(apiKey, userProfiles);    	
        return new ResponseEntity<List<UserProfile>>(userProfiles, HttpStatus.CREATED);
  
   	}
}
