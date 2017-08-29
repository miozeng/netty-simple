package com.mio.netty.seri;

import java.io.Serializable;

import com.mio.netty.chat.common.ChatConstants;

public class UserInfo  implements Serializable{
	private static final long serialVersionUID = 3562768188264006800L;

	private Long id;

	private String phone;

	private String password;
	
	private String code;
	
	private String headImg;
	
	public UserInfo() {
		
	}
	
	public UserInfo(String phone) {
		this.phone = phone;
		this.headImg = ChatConstants.headImg();
		this.code = ChatConstants.code();
		this.id = System.currentTimeMillis();
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
