package egovframework.example.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.user.dto.UserDto;
import egovframework.example.user.dto.UserSaveForm;
import egovframework.example.user.service.impl.UserDao;
import egovframework.example.user.service.impl.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final UserDao userDao;
	
	@Autowired
	public UserServiceImpl (UserDao userDao) {
		this.userDao = userDao; 
	}

	public UserDto getByUsername(UserDto userDto) throws Exception {
		return userDao.getByUsername(userDto);
	}

	public void create(UserSaveForm userSaveForm) throws Exception {
		UserDto userDto = new UserDto.Builder()
							  .username(userSaveForm.getUsername())
							  .password(userSaveForm.getPassword())
							  .build();
		userDao.create(userDto);
	}

}
