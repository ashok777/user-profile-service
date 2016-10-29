package com.poc.userprofile.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.poc.userprofile.model.UserProfile;


@PropertySource("classpath:app.properties")
@Component
public class LocalDataStoreDAO implements UserProfileDAO {
	
	@Value("${local.datastore.file.path}")
	private String localDataStoreFilePath;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public List<UserProfile> readUserProfiles() throws Exception{
		
		List<UserProfile> userProfiles = new ArrayList<UserProfile>();
		
		File dataFile = new File(localDataStoreFilePath);
		if (dataFile.exists()){

			JavaType listType =	objectMapper.getTypeFactory().constructCollectionType(List.class, UserProfile.class);
			userProfiles = 	objectMapper.readValue(new File(localDataStoreFilePath), listType);
		}
		
		return userProfiles;	
	}
	
	public void   persistUserProfiles(List<UserProfile> newProfiles) throws Exception{
	
		List<UserProfile> userProfiles = readUserProfiles();
		userProfiles.addAll(newProfiles);
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.writeValue(new File(localDataStoreFilePath), userProfiles);
		
	}
}
