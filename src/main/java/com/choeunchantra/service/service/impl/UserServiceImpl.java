package com.choeunchantra.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.choeunchantra.service.exception.UserServiceException;
import com.choeunchantra.service.io.entity.UserEntity;
import com.choeunchantra.service.repositories.UserRepository;
import com.choeunchantra.service.service.UserService;
import com.choeunchantra.service.shared.dto.UserDto;
import com.choeunchantra.service.shared.dto.Utils;
import com.choeunchantra.service.ui.model.responses.ErrorMessages;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Utils utils;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		if(userRepository.findByEmail(userDto.getEmail()) != null ) throw new RuntimeException("Email alreadt exist");
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		userEntity.setUserId(utils.generateUserId(30));
		UserEntity storedUserDtails = userRepository.save(userEntity);
		
		UserDto returnValue = new UserDto();
		System.out.println("return Value: " + returnValue.toString());
		
		BeanUtils.copyProperties(storedUserDtails, returnValue);
		return returnValue;
	}
	
	@Override
	public UserEntity findByEmail(String email) {
		UserEntity entity = userRepository.findByEmail(email);
		return entity;
	}
	
	@Override
	public UserDto getUser(String email) {
		UserEntity entity = userRepository.findByEmail(email);
		
		if(entity == null) {
			throw new UsernameNotFoundException(email);
		}
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(entity, returnValue);
		
		return returnValue;
	}
	
	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity entity = userRepository.findByUserId(userId);
		
		if(entity == null) {
			throw new UsernameNotFoundException(userId);
		}
		
		UserDto userDetails = new UserDto();
		BeanUtils.copyProperties(entity, userDetails);
		return userDetails;
	}
	
	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		UserEntity entity = userRepository.findByUserId(userId);
		
		if(entity == null) {
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		
		entity.setFirstName(userDto.getFirstName());
		entity.setLastName(userDto.getLastName());
		
		UserEntity updatedUser = userRepository.save(entity);
		
		BeanUtils.copyProperties(updatedUser, userDto);
		return userDto;
	}
	
	@Override
	public void deleteUser(String userId) {
		UserEntity entity = userRepository.findByUserId(userId);
		
		if(entity == null) {
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		
		userRepository.delete(entity);
	}
	
	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<UserDto>();
		
		Pageable pageable = PageRequest.of(page, limit);
		
		Page<UserEntity> userPage = userRepository.findAll(pageable);
		
		List<UserEntity> listUsers = userPage.getContent();
		
		for(UserEntity user: listUsers) {
			UserDto userDto = new UserDto();			
			BeanUtils.copyProperties(user, userDto);
			returnValue.add(userDto);
		}
		
		// TODO Auto-generated method stub
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity entity = userRepository.findByEmail(email);
		
		if(entity == null) {
			throw new UsernameNotFoundException(email);
		}
		return new User(entity.getEmail(), entity.getEncryptedPassword(), new ArrayList<>());
	}
}
