package cn.hyn123.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hyn123.dao.EmailCaptchaDao;
import cn.hyn123.dao.UserDao;
import cn.hyn123.entities.EmailCaptcha;
import cn.hyn123.service.EmailCaptchaService;

@Service
public class EmailCaptchaServiceImpl implements EmailCaptchaService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private EmailCaptchaDao emailCaptchaDao;

	@Transactional
	@Override
	public void sendEmail(String email, String captcha) throws Exception {
		/**
		 * 第一步创建Session
		 */
		Properties properties = new Properties();
		properties.setProperty("mail.host", "smtp.163.com");
		properties.setProperty("mail.smtp.auth", "true");

		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("18577313534", "hyn2012332");
			}
		};

		Session session = Session.getInstance(properties, authenticator);

		/**
		 * 第二步创建MimeMessage
		 */
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("18577313534@163.com"));
		msg.setRecipients(RecipientType.TO, email);

		msg.setSubject("天地图在线查询与管理");
		String content = MessageFormat.format("尊敬的{0}，您好！您的验证码为:{1}，验证码的有效期为二十分钟。如果您没有绑定邮箱，请忽略这封邮件！",
				userDao.findUserByEmail(email).getUserName(), captcha);
		msg.setContent(content, "text/html;charset=utf-8");

		/**
		 * 第三步发送邮件
		 */
		Transport.send(msg);
	}

	@Override
	public int checkEmailCaptcha(String email, String captcha) throws Exception {
		// 获取验证码实体类
		EmailCaptcha userCaptcha = emailCaptchaDao.getUserCaptcha(email);

		// 如果找不到，那么返回0。表示验证码已经过期
		if (userCaptcha == null) {
			return 0;
		}

		// 获取当前时间和验证码产生时间
		Date now = new Date();
		Date createTime = userCaptcha.getCreateTime();

		// 等到毫秒为单位的时间差
		long min = now.getTime() - createTime.getTime();

		// 如果验证码过期，返回0
		if (min > 20 * 60 * 1000) {
			return 0;
		} else {
			// 判断是否输入正确的验证码
			Boolean isOk = captcha.equals(userCaptcha.getCaptcha());
			// 如果输入正确
			if (isOk) {
				return 1;
			} else {
				return -1;
			}
		}
	}

}
