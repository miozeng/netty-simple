package com.mio.netty.chat.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mio.netty.chat.modul.UserInfo;

@Controller
public class LoginController {

	
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String doLogin(UserInfo user) {
		UserInfo.map.put(user.getPhone(), user);
		//return "redirect:/msg/list?token="+user.getPhone();
		return "redirect:/chat/list?token="+user.getPhone();
	}
}