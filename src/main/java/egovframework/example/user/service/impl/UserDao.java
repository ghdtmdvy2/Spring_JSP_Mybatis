package egovframework.example.user.service.impl;

import java.math.BigInteger;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import egovframework.example.egov.service.impl.EgovComAbstractDAO;
import egovframework.example.user.dto.UserDto;

@Repository
public class UserDao extends EgovComAbstractDAO {
	public UserDto getByUsername(UserDto userDto) throws Exception {		
		HashMap<?, ?> test = (HashMap<?, ?>) getSqlMapClient().queryForObject("UserDao.getByUsername", userDto);
		String password = (String) test.get("password");
		String username = (String) test.get("username");
		BigInteger bigIntegerId = (BigInteger) test.get("id");
		Long id = bigIntegerId.longValue();
		UserDto findUserDto = new UserDto.Builder()
								  .id(id)
								  .username(username)
								  .password(password)
								  .build();		
		return findUserDto;
	}

	public void create(UserDto userDto) throws Exception{
		getSqlMapClient().insert("UserDao.create", userDto);
	}
}
