package com.poc.userprofile.persistence;

import java.io.File;
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
	
	public Map<String,List<UserProfile>> readUserProfiles() throws Exception{
		
		Map<String, List<UserProfile>> map = new HashMap<String, List<UserProfile>>();
		
		File dataFile = new File(localDataStoreFilePath);
		if (dataFile.exists()){
			
			JavaType mapValueType =	objectMapper.getTypeFactory().constructCollectionType(List.class, UserProfile.class);
	        JavaType mapKeyType = objectMapper.getTypeFactory().constructType(String.class);
			JavaType mapType = 	objectMapper.getTypeFactory().constructMapType(Map.class, mapKeyType, mapValueType);	
			
			map = 	objectMapper.readValue(new File(localDataStoreFilePath),mapType);
		}
		
		
		return map;	
		
	}
	
	public void   persistUserProfiles(Map<String,List<UserProfile>> userProfilesMap) throws Exception{
	
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.writeValue(new File(localDataStoreFilePath), userProfilesMap);
		
	}
}
