package dao;

import entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
	/**
	 * 根据用户名和密码查找用户
	 */
	User findUser(@Param("userName")String userName,@Param("passWord")String passWord) throws Exception;

	List<User> listUser() throws Exception;
}
