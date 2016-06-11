package cn.hyn123.service;

import cn.hyn123.entities.User;

/**
 * 用户注册
 * 
 * @author Administrator
 *
 */
public interface UserRegService {
	
	/**
	 * 用户注册
	 * 
	 * @param user 新增加的用户实体类
	 */
	public void reg(User user) throws Exception;
	

}
