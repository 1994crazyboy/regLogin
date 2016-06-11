package cn.hyn123.entities;

import java.util.Date;

/**
 * 数据库中的用户表的映射实体类
 * 
 * @author Administrator
 *
 */
public class User {
	private int id;
	
	private String userName;
	private String passWord;
	private String email;
	private String phone;
	
	private Date regTime;
	private Byte status;
	
	
	
	
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", email=" + email + ", phone="
				+ phone + ", regTime=" + regTime + "]";
	}
	
	
	
}
