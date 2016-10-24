package com.poc.userprofile.service;

public interface AddressResolutionProvider {

	public String getResolvedAddress(String apiKey, String incompleteAddress) throws Exception;
}
