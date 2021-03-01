package com.choeunchantra.service.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.choeunchantra.service.SpringApplicationContext;
import com.choeunchantra.service.service.UserService;
import com.choeunchantra.service.service.impl.UserServiceImpl;
import com.choeunchantra.service.shared.dto.UserDto;
import com.choeunchantra.service.ui.model.requests.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserLoginRequestModel userCredentail = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					userCredentail.getEmail(), userCredentail.getPassword(), new ArrayList<>()));
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String userName = ((User)authResult.getPrincipal()).getUsername();
		
		String token = Jwts.builder()
				.setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
				.compact();
		
		UserService userService = (UserService)SpringApplicationContext.getBean("userServiceImpl");
		UserDto userDetails = userService.getUser(userName);
		
		response.setHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
		response.setHeader("UserID", userDetails.getUserId());
	}
}
