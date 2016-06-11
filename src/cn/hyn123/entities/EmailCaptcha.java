package cn.hyn123.entities;

import java.util.Date;

/**
 * 用户邮箱验证码实体类
 * @author Administrator
 *
 */
public class EmailCaptcha {
	
	private int id;				//验证码id
	private String email;		//用户邮箱
	private String captcha;		//验证码
	private Date createTime;	//创建时间
	
	
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "EmailCaptcha [id=" + id + ", email=" + email + ", captcha=" + captcha + ", createTime=" + createTime
				+ "]";
	}
	
	

}
