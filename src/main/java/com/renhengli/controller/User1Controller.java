package com.renhengli.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.renhengli.entity.User;
import com.renhengli.exception.MyException;
import com.renhengli.mapper.UserMapper;
import com.renhengli.service.DemoService;

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

	@Autowired
	DemoService demoService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, User> redisTemplate;

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

	@RequestMapping("/test")
	@ResponseBody
	public String putCache() throws MyException {
		try {
			//demoService.findUser(1l, "wang", 20);
			System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e.getMessage());
		}
	}

	@RequestMapping("/test2")
	@ResponseBody
	public String testCache() {
		//User user = demoService.findUser(1l, "wang", 22);
		System.out.println("我这里没执行查询");
		//System.out.println("user:" + "/" + user.getName() + "/" + user.getAge());
		return "ok";
	}

	/**
	 * 测试自定义异常
	 * 
	 * @return
	 * @throws MyException
	 */
	@RequestMapping("/json")
	public String json() throws MyException {
		throw new MyException("发生错误2");
	}

	@RequestMapping("/hello")
	public String hello() throws Exception {
		throw new Exception("发生错误");
	}

	@RequestMapping("/redis")
	@ResponseBody
	public String redis() throws Exception {
		// 保存字符串
		stringRedisTemplate.opsForValue().set("aaa", "111");
		System.out.println("------------"+stringRedisTemplate.opsForValue().get("aaa")+"--------------------");
		// 保存对象
		User user = new User(1l, "超人", 20);
		redisTemplate.opsForValue().set(user.getName(), user);

		user = new User(2l, "蝙蝠侠", 30);
		redisTemplate.opsForValue().set(user.getName(), user);

		user = new User(3l, "蜘蛛侠", 40);
		redisTemplate.opsForValue().set(user.getName(), user);
		return "ok";
	}

}
