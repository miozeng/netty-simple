package com.mio.netty.chat.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mio.netty.chat.common.ChatConstants;
import com.mio.netty.chat.modul.UserInfo;

@Controller
@RequestMapping("/chat")
public class ChatController {

	// 跳转到交谈聊天页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void talk(String token, HttpServletResponse response) throws IOException {
//		model.addAttribute("token", token);
		response.sendRedirect("../chat.html#"+token);
	}
	
	@ResponseBody
	@RequestMapping(value = "users", method = RequestMethod.GET, produces={"application/json; charset=UTF-8", "text/plain"})
	public String users(String token) {
		Map<String, UserInfo> onlines = ChatConstants.onlines;
		UserInfo cur = onlines.get(token);
		
		Map<String, Object> map = new HashMap<>(2);	
		map.put("curName", cur!=null?cur.getCode():"");
		map.put("users", onlines);
		return JSON.toJSONString(map);
	}
}