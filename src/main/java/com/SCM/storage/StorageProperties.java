package com.SCM.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {
	
	private String location = "images";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public StorageProperties(String location) {
		super();
		this.location = location;
	}

	public StorageProperties() {
		
	}
	
	

}
