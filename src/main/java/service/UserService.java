package service;

import entity.User;

import java.util.List;

public interface UserService {
	/**
	 * 根据用户名和密码查找用户
	 */
	User findUser(String userName, String passWord) throws Exception;

	List<User> listUser() throws Exception;
}