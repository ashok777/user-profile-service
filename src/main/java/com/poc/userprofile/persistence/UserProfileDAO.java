package com.poc.userprofile.persistence;

import java.util.List;

import com.poc.userprofile.model.UserProfile;

public interface UserProfileDAO {

	public List<UserProfile> readUserProfiles()  throws Exception;
	
	public void   persistUserProfiles(List<UserProfile> userProfiles) throws Exception;
}
