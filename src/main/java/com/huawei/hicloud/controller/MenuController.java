package com.huawei.hicloud.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.huawei.hicloud.po.menu.Button;
import com.huawei.hicloud.service.IMenuService;
import com.huawei.hicloud.vo.ResultMap;

@RestController
@RequestMapping(value = "/wechart")
public class MenuController {

	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private IMenuService iMenuService;
	
	@RequestMapping(value="/menu/create", method=RequestMethod.POST)
	public ResultMap<Object> createMenu(@RequestParam String appId, @RequestBody Map<String, List<Button>> menuParams) {
		
		logger.info("Menu create params: appId: {}, menuParams: {}.", JSON.toJSONString(menuParams));
		ResultMap<Object> resultMap = iMenuService.create(appId, menuParams);
		logger.info("Menu create result: {}.", JSON.toJSONString(resultMap));
		
		return resultMap;
	}
	
	@RequestMapping(value="/menu/get", method=RequestMethod.GET)
	public ResultMap<Object> getMenu(@RequestParam String appId) {
		
		logger.info("Start get menu.");
		ResultMap<Object> resultMap = iMenuService.get(appId);
		logger.info("Get Menu result: {}.", JSON.toJSONString(resultMap));
		
		return resultMap;
	}
	
	@RequestMapping(value="/menu/delete", method=RequestMethod.GET)
	public ResultMap<Object> deleteMenu(@RequestParam String appId) {
		
		logger.info("Start delete menu.");
		ResultMap<Object> resultMap = iMenuService.delete(appId);
		logger.info("Delete Menu result: {}.", JSON.toJSONString(resultMap));
		
		return resultMap;
	}
	
}
