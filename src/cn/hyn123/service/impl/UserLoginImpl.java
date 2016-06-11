package cn.hyn123.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hyn123.algorithm.MD5;
import cn.hyn123.dao.UserDao;
import cn.hyn123.entities.User;
import cn.hyn123.service.UserLogin;

@Service
public class UserLoginImpl implements UserLogin {
	
	@Autowired
	private UserDao userDao;

	
	@Transactional
	@Override
	public int login(String email, String passWord) throws Exception {
		User user=userDao.findUserByEmail(email);
		if(user==null){
			return 0;		//用户不存在就返回0
		}else{
			if(MD5.getMD5(passWord).equals(user.getPassWord())){
				return 2;	//用户密码正确返回2
			}else{
				return 1;	//用户密码错误返回1
			}
		}
	}

}
