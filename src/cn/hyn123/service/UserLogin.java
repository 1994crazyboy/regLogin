package cn.hyn123.service;

/**
 * 用户登录
 * 
 * @author Administrator
 *
 */
public interface UserLogin {
	
	/**
	 * 用户登录，根据邮箱进行登录
	 * @param email		登录的邮箱地址
	 * @param passWord	登录的密码
	 * @return 
	 * @throws Exception 
	 */
	public int login(String email,String passWord) throws Exception;
	
	
	

}
