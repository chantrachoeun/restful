package com.choeunchantra.service.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable{
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private static final long serialVersionUID = -234758361482236120L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false, length = 50)
	private String firstName;
	
	@Column(nullable = false, length = 50)
	private String lastName;
	
	@Column(nullable = false, length = 120, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String encryptedPassword;
	
	private String emailVerificationToken;
	
	@Column(nullable = false)
	private Boolean emailVerificationStatus = false;
}
