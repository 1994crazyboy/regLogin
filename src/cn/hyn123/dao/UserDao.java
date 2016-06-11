package cn.hyn123.dao;

import org.apache.ibatis.annotations.Param;

import cn.hyn123.entities.User;

public interface UserDao {
	/*
	 * 查找用户
	 */
	public User findUserById(int id) throws Exception;
	public User findUserByUserName(String userName) throws Exception;
	public User findUserByEmail(String email) throws Exception;
	public User findUserByPhone(String phone) throws Exception;

	/*
	 * 插入更新和删除
	 */
	public int insertUser(User user) throws Exception;
	public void deleteUser(int id) throws Exception;

	/*
	 * 更改用户密码、手机号码以及激活邮箱
	 */
	public int midifyPassWord(@Param("userName") String userName, @Param("newPassWord") String newPassWord)
			throws Exception;
	public int midifyPhone(@Param("userName") String userName, @Param("newPhone") String newPhone) throws Exception;
	public int activateUser(String email) throws Exception;

}
