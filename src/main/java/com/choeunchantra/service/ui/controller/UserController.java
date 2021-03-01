package com.choeunchantra.service.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.ListAttribute;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.choeunchantra.service.exception.UserServiceException;
import com.choeunchantra.service.io.entity.UserEntity;
import com.choeunchantra.service.repositories.UserRepository;
import com.choeunchantra.service.service.UserService;
import com.choeunchantra.service.shared.dto.UserDto;
import com.choeunchantra.service.ui.model.requests.UserDetailRequestModel;
import com.choeunchantra.service.ui.model.responses.ErrorMessages;
import com.choeunchantra.service.ui.model.responses.UserRest;

@RestController
@RequestMapping("/users")
public class UserController {
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService; 
	}
	
	@GetMapping(path = "/{id}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest getUser(@PathVariable String id) {
		UserDto userDetails = userService.getUserByUserId(id);
		UserRest userRest = new UserRest();
		BeanUtils.copyProperties(userDetails, userRest);
		
		return userRest;
	}
	
	@PostMapping(
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public UserRest createUser(@RequestBody UserDetailRequestModel userDetails) {
		if(userDetails.getEmail().isEmpty() || userDetails.getEmail() == null) 
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		if(userDetails.getPassword().isEmpty() || userDetails.getPassword() == null) 
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createUser, returnValue);
		return returnValue;
	}
	
	@PutMapping(
			path = "/{id}",
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailRequestModel userDetails) {
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(createUser, returnValue);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "5") int limit){
		ArrayList<UserRest> returnValue = new ArrayList<>();
		
		List<UserDto> users = userService.getUsers(page, limit);
		
		for(UserDto user: users) {
			UserRest userRest = new UserRest();	
			BeanUtils.copyProperties(user, userRest);
			returnValue.add(userRest);
		}
		
		return returnValue;
	}
}
