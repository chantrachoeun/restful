package com.choeunchantra.service.security;

import com.choeunchantra.service.SpringApplicationContext;

public class SecurityConstants {	
	public static final long EXPIRATION_TIME = 864000000;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	// public static final String TOKEN_SECRET = "chantra08101996@";
	
	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties)SpringApplicationContext.getBean("appProperties");
		
		return appProperties.getTokenSecret();
	}
}
