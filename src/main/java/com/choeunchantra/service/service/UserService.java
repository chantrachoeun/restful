package com.choeunchantra.service.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.choeunchantra.service.io.entity.UserEntity;
import com.choeunchantra.service.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto userDto);
	UserEntity findByEmail(String email);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String userId, UserDto userDetail);
	void deleteUser(String userId);
	List<UserDto> getUsers(int page, int limit);
}
