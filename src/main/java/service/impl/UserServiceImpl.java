package service.impl;

import dao.UserMapper;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
	private final UserMapper userMapper;
	@Autowired
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	/**
	 * 根据用户名和密码查找用户
	 */
	@Override
	public User findUser(String userName,String passWord) throws Exception{
		return userMapper.findUser(userName,passWord);
	}

	@Override
	public List<User> listUser() throws Exception {
		return userMapper.listUser();
	}
}
