package cn.hyn123.dao;

import cn.hyn123.entities.EmailCaptcha;

public interface EmailCaptchaDao {

	/**
	 * 输入验证码实体类，保存验证码
	 * @param emailCaptcha	验证码
	 * @return				返回影响的行数
	 */
	public int saveCaptcha(EmailCaptcha emailCaptcha);
	

	/**
	 * 输入用户邮箱。返回验证码实体类
	 * @param email		用户邮箱
	 * @return			返回验证码实体类
	 */
	public EmailCaptcha getUserCaptcha(String email);
	
	/**
	 * 验证成功之后删除用户的验证码
	 * @param email
	 * @return
	 */
	public int deleteCaptcha(String email);

}
