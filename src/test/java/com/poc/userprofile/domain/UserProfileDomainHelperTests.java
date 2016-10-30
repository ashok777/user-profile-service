
package com.poc.userprofile.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.poc.userprofile.app.Application;
import com.poc.userprofile.model.UserProfile;
import com.poc.userprofile.persistence.UserProfileDAO;
import com.poc.userprofile.service.*;
import static org.mockito.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)

@SpringBootTest(classes =Application.class)

public class UserProfileDomainHelperTests {

	@Autowired
	UserProfileDomainHelper userProfileDomainHelper;

	@MockBean
    private AddressResolutionProvider  addressResolutionProvider;
	
	@MockBean
    private UserProfileDAO  userProfileDAO;
	
    @Test
    public void resolveAddressesAndSave() throws Exception {

    	/* Mock the address resolution service to return the provided resolved address*/
    	
    	String unresolvedAddress = "1234 Main St, Saratoga CA";
     	String resolvedAddress = unresolvedAddress + " 95070";
     	
    	Mockito.doReturn(resolvedAddress).when(this.addressResolutionProvider).getResolvedAddress(anyString(),anyString());
    	    	
    	/* stub out the userProfileDAO persistence method*/
    	Mockito.doNothing().when(userProfileDAO).persistUserProfiles(anyListOf(UserProfile.class));
    
    	/* set up a profile with unresolved address and add to a list*/
    	UserProfile up = new UserProfile();
		up.setName("John Doe");
		up.setAddress(unresolvedAddress);
		List<UserProfile> list = new ArrayList<UserProfile>();
		list.add(up);
		
		/* Pass in profile list with unresolved address and expect resolved address in the returned profile*/
		
    	List<UserProfile> resolvedList = userProfileDomainHelper.resolveAddressesAndSave("someKey", list );
    	assertTrue(resolvedList.get(0).getAddress().equals(resolvedAddress));
    	
    }
}
