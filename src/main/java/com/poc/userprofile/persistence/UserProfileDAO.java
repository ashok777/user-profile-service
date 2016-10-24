package com.poc.userprofile.persistence;

import java.util.List;
import java.util.Map;

import com.poc.userprofile.model.UserProfile;

public interface UserProfileDAO {

	public Map<String,List<UserProfile>> readUserProfiles()  throws Exception;
	
	public void   persistUserProfiles(Map<String,List<UserProfile>> userProfilesMap) throws Exception;
}
