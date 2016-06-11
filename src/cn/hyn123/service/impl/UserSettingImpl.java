package cn.hyn123.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hyn123.dao.EmailCaptchaDao;
import cn.hyn123.dao.UserDao;
import cn.hyn123.service.UserSetting;

@Service
public class UserSettingImpl implements UserSetting {

	@Autowired
	private UserDao userDao;
	@Autowired
	private EmailCaptchaDao emailCaptchaDao;
	
	@Transactional
	@Override
	public void bindPhone(String userName,String newPhone) throws Exception {
		userDao.midifyPhone(userName, newPhone);
		
	}

	@Transactional
	@Override
	public void resetPassWord(String userName, String newPassWord) throws Exception {
		userDao.midifyPassWord(userName, newPassWord);
	}

	@Transactional
	@Override
	public void activateUser(String email) throws Exception {
		userDao.activateUser(email);
		emailCaptchaDao.deleteCaptcha(email);
	}
	
	



}
