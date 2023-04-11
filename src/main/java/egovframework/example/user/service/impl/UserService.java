package egovframework.example.user.service.impl;

import egovframework.example.user.dto.UserDto;
import egovframework.example.user.dto.UserSaveForm;

public interface UserService {
	public UserDto getByUsername(UserDto userDto) throws Exception;
	public void create(UserSaveForm userSaveForm) throws Exception;
}	
