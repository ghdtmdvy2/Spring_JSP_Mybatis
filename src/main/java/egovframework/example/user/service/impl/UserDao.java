package egovframework.example.user.service.impl;

import org.springframework.stereotype.Repository;

import egovframework.example.egov.service.impl.EgovComAbstractDAO;
import egovframework.example.user.dto.UserDto;

@Repository
public class UserDao extends EgovComAbstractDAO {
	public UserDto getByUsername(UserDto userDto) throws Exception {	
		UserDto findUserDto = (UserDto) getSqlMapClient().queryForObject("UserDao.getByUsername", userDto);
		return findUserDto;
	}

	public void create(UserDto userDto) throws Exception{
		getSqlMapClient().insert("UserDao.create", userDto);
	}
}
