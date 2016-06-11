package cn.hyn123.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hyn123.dao.UserDao;
import cn.hyn123.entities.User;
import cn.hyn123.service.UserRegService;

@Service
public class UserRegServiceImpl implements UserRegService {

	@Autowired
	private UserDao userDao;
	
	@Transactional
	@Override
	public void reg(User user) throws Exception {
		userDao.insertUser(user);
	}

	
}
