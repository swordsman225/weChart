package com.huawei.hicloud.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/lettuce")
public class RedisLettuceController {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisLettuceController.class);
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@RequestMapping(value="/string")
	public Map<String, Object> testString(@RequestParam String key, @RequestParam String value) {
		logger.info("Request params: key: {}, value: {}.", key, value);
		
		redisTemplate.opsForValue().set(key, value, 600L, TimeUnit.SECONDS);
		
		redisTemplate.opsForHash().put("kab", "abc" + (int)(Math.random() * 100), value);
		
		
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put(key, value);
		
		return resultMap; 
	}
	
}
