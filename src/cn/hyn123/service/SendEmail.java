package cn.hyn123.service;

public interface SendEmail {
	/**
	 * 发送验证码
	 * @param email
	 * @param captcha
	 * @throws Exception 
	 */
	public void sendEmail(String email,String captcha) throws Exception;

}
