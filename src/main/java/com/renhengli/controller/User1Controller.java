package com.renhengli.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.renhengli.mapper.UserMapper;

/**
 * 
 * @author renhengli
 *
 */
@Controller
@RequestMapping("/home")
public class User1Controller {
	private static Logger logger = Logger.getLogger(User1Controller.class);
	
	@Autowired
	UserMapper userMapper;

	// 从 application.properties 中读取配置，如取不到默认值为Hello Shanhy
	@Value("${application.hello:Hello Angel}")
	private String hello;
	

	@RequestMapping("/user1")
	public String user1(Map<String, Object> map) {
		logger.info("-----start-------");
		logger.debug("-----debug log-------");
		logger.error("-----error log-------");
		System.out.println("UserController.user1().hello=" + hello);
		map.put("hello", hello);
		logger.debug("-----end---------");
		logger.info("-----end---------");
		return "user";
	}
}
