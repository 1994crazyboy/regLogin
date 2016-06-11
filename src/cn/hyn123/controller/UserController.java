package cn.hyn123.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.hyn123.algorithm.MD5;
import cn.hyn123.dao.EmailCaptchaDao;
import cn.hyn123.dao.UserDao;
import cn.hyn123.entities.EmailCaptcha;
import cn.hyn123.entities.User;
import cn.hyn123.service.CaptchaService;
import cn.hyn123.service.EmailCaptchaService;
import cn.hyn123.service.UserLoginService;
import cn.hyn123.service.UserRegService;
import cn.hyn123.service.UserSettingService;
import cn.hyn123.service.impl.CaptchaServiceSingleton;

@Controller
public class UserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private UserRegService userRegService;
	@Autowired
	private UserSettingService userSettingService;
	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private EmailCaptchaService emailCaptchaService;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private EmailCaptchaDao emCaptchaDao;

	private static Boolean isCaptcha;

	public static Boolean getIsCaptcha() {
		return isCaptcha;
	}

	public static void setIsCaptcha(Boolean isCaptcha) {
		UserController.isCaptcha = isCaptcha;
	}

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

	/**
	 * 跳转到注册页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/reg")
	public String reg(Model model) {
		return "reg";
	}

	/**
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/signin")
	public ModelAndView signin(String userName, String passWord, String email, String captcha, HttpSession httpSession)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// 如果验证码不正确
		if (!isCaptcha) {
			modelAndView.addObject("info", "<span class='help-inline' style='color: #ff0000'>验证码不正确！</span>");
			modelAndView.setViewName("reg");
			return modelAndView;
		}

		// 创建新的用户
		User user = new User();
		user.setUserName(userName);
		user.setEmail(email);
		user.setPassWord(MD5.getMD5(passWord));
		user.setRegTime(new Date());
		user.setStatus((byte) 0);

		// UserReg注册用户
		userRegService.reg(user);

		// 设置传到前台的信息和视图名称

		// 设置session
		modelAndView.setViewName("redirect:index.jsp");

		httpSession.setAttribute("status", user.getStatus());
		httpSession.setAttribute("userName", userName);
		httpSession.setAttribute("email", email);
		httpSession.setAttribute("phone", user.getPhone());
		httpSession.setAttribute("regTime", user.getRegTime());

		return modelAndView;
	}

	/**
	 * 登录用户
	 * 
	 * @param email
	 * @param passWord
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginSuccess")
	public ModelAndView loginSuccess(String email, String passWord, HttpSession httpSession) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		// 登录用户，并将登录后的状态码返回，如果是0用户不存在，如果是1那么密码错误，如果是2那么密码正确
		int result = userLoginService.login(email, passWord);

		// 查找这个用户
		User user = userDao.findUserByEmail(email);

		if (result == 2) {
			// 如果是2，那么登录成功，返回index
			modelAndView.setViewName("redirect:index.jsp");

			// 设置session
			httpSession.setAttribute("status", user.getStatus());
			httpSession.setAttribute("userName", user.getUserName());
			httpSession.setAttribute("email", email);
			httpSession.setAttribute("phone", user.getPhone());
			httpSession.setAttribute("regTime", user.getRegTime());

		} else if (result == 1) {
			// 如果是1，那么密码错误，返回login
			modelAndView.addObject("info", "<span class='help-inline' style='color: #ff0000'>密码错误！</span>");
			modelAndView.setViewName("login");
		} else {
			// 否则用户名不存在，返回login
			modelAndView.addObject("info", "<span class='help-inline' style='color: #ff0000'>用户不存在！</span>");
			modelAndView.addObject("info", 0);
			modelAndView.setViewName("login");
		}

		return modelAndView;
	}

	/**
	 * 处理ajax请求，检验用户名和邮箱以及验证码
	 * 
	 * @param userName
	 * @param email
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/check")
	public @ResponseBody int check(String userName, String email) throws Exception {
		if (userName != null) {
			// 如果请求的是userName
			User user = userDao.findUserByUserName(userName);

			// 检查user是否为空，如果为空返回0，如果不为空返回1
			if (user == null) {
				return 0;
			} else {
				return 1;
			}

		} else if (email != null) {
			// 如果请求的是email
			User user = userDao.findUserByEmail(email);
			// 检查user是否为空，如果为空返回0，如果不为空返回1
			if (user == null) {
				return 0;
			} else {
				return 1;
			}

		}
		return 0;
	}
	
	
	
	/**
	 * 生成验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/captcha")
	public @ResponseBody void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		captchaService.genernateCaptchaImage(request, response);
		isCaptcha = Boolean.FALSE;
	}

	/**
	 * 检查用户输入的验证码是否正确
	 * 
	 * @param captcha
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkCaptcha")
	public @ResponseBody int checkCaptcha(String captcha, HttpServletRequest request) throws Exception {
		Boolean isResponseCorrect =  CaptchaServiceSingleton.getInstance().validateResponseForID(request.getSession().getId(), captcha);
		if (isResponseCorrect) {
			isCaptcha = Boolean.TRUE;
			return 1;
		} else {
			isCaptcha = Boolean.FALSE;
			return 0;
		}
	}

	

	@RequestMapping("/sendCaptchaEmail")
	public @ResponseBody int sendCaptchaEmail(String email) throws Exception {
		// 生成验证码
		String captcha = getCaptcha();
		
		//发送邮件
		emailCaptchaService.sendEmail(email, captcha);

		// 发送成功之后将验证码保存到数据库
		EmailCaptcha emailCaptcha = new EmailCaptcha();
		emailCaptcha.setCaptcha(captcha);
		emailCaptcha.setEmail(email);
		emailCaptcha.setCreateTime(new Date());

		emCaptchaDao.saveCaptcha(emailCaptcha);

		return 1;

	}

	/**
	 * 验证用户输入的验证码是否正确，如果验证码过期返回0，如果验证码不正确返回-1，验证码正确返回1
	 * 
	 * @param email
	 *            用户邮箱
	 * @param captcha
	 *            验证码
	 * @return
	 */
	@RequestMapping("/checkEmailCaptcha")
	public @ResponseBody int checkEmailCaptcha(String email, String captcha) {
		// 获取验证码实体类
		EmailCaptcha userCaptcha = emCaptchaDao.getUserCaptcha(email);

		logger.info("传入的邮箱为：" + email + "传入的验证码为：" + captcha);

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
			System.out.println("验证码的时间为：" + min);
			logger.info("你大爷的啊，验证码过期了！");
			return 0;
		} else {
			// 判断是否输入正确的验证码
			Boolean isOk = captcha.equals(userCaptcha.getCaptcha());
			// 如果输入正确
			if (isOk) {
				logger.info("很好，验证码正确了");
				return 1;
			} else {
				logger.info("卧槽，你验证码输错了！");
				return -1;
			}
		}
	}

	/**
	 * 更改用户的状态
	 * @throws Exception 
	 */
	@RequestMapping("/activateUser")
	public @ResponseBody int activateUser(String email,String captcha,HttpSession httpSession) throws Exception {
		
		EmailCaptcha emailCaptcha = emCaptchaDao.getUserCaptcha(email);
		Boolean isOk=captcha.equals(emailCaptcha.getCaptcha());
		//如果验证码正确
		if(isOk){
			userSettingService.activateUser(email);
			return 1;
		}
		return 0;
	}

	/**
	 * 请求注销用户
	 * 
	 * @return
	 */
	@RequestMapping("/loginOff")
	public @ResponseBody int loginOff(HttpServletRequest httpServletRequest) {
		try {
			HttpSession httpSession = httpServletRequest.getSession();
			httpSession.invalidate();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 随机产生验证码函数
	 * 
	 * @return
	 */
	public String getCaptcha() {
		// 验证码的可选字符
		char[] captChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		// 定义随机数变量
		Random r = new Random();
		int len = captChar.length, index;
		StringBuffer captcha = new StringBuffer();
		// 随机产生6位验证码
		for (int i = 0; i < 6; i++) {
			index = r.nextInt(len);
			captcha.append(captChar[index]);
		}
		return captcha.toString();
	}

}
