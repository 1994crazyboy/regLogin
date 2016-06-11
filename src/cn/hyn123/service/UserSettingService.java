package cn.hyn123.service;

/**
 * 用户设置类： 
 * 	1、用户更改密码 
 * 	2、用户更改邮箱 
 * 	3、用户绑定手机号码
 * 
 * @author Administrator
 *
 */
public interface UserSettingService {

	/**
	 * 绑定用户手机
	 * 
	 * @param phone		绑定的手机号码
	 * @throws Exception 
	 */
	public void bindPhone(String userName,String newPhone) throws Exception;
	
	/**
	 * 激活邮箱，将用户记录的status改为1 
	 * 
	 * @param email			用户邮箱
	 * @throws Exception
	 */
	public void activateUser(String email) throws Exception;

	/**
	 * 更改用户密码
	 * 
	 * @param userName		用户名
	 * @param newPassWord	新的密码
	 */
	public void resetPassWord(String userName,String newPassWord) throws Exception;

}
