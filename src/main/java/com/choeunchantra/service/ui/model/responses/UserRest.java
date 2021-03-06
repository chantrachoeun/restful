package com.choeunchantra.service.ui.model.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRest {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
}
