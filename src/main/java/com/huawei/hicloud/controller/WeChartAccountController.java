package com.huawei.hicloud.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.huawei.hicloud.po.WeChartAccount;
import com.huawei.hicloud.service.IWeChartAccountService;
import com.huawei.hicloud.vo.ResultMap;

/**
 * WeChat Official Account.
 * @author JiaxuYan
 *
 */
@RestController
@RequestMapping(value="/wechart/accounts")
public class WeChartAccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(WeChartAccountController.class);

	@Autowired
	private IWeChartAccountService iWeChartAccountService;
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResultMap<WeChartAccount> create(@RequestBody WeChartAccount account) {
		logger.info("WeChart account create params: {}.", JSON.toJSONString(account));
		
		Boolean result = iWeChartAccountService.create(account);
		ResultMap<WeChartAccount> resultMap = null;
		if (result) {
			resultMap = ResultMap.success(account);
		} else {
			resultMap = ResultMap.fail("Create weChart account fail!");
		}
		logger.info("WeChart account create result: {}.", JSON.toJSONString(resultMap));
		
		return resultMap;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResultMap<WeChartAccount> findByPK(@PathVariable String id) {
		logger.info("WeChart account findByPK params: {}.", id);
		
		WeChartAccount weChartAccount = iWeChartAccountService.findByPK(id);
		ResultMap<WeChartAccount> resultMap = ResultMap.success(weChartAccount);
		logger.info("WeChart account findByPK result: {}.", JSON.toJSONString(resultMap));
		
		return resultMap;
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResultMap<List<WeChartAccount>> query(WeChartAccount account) {
		logger.info("WeChart account query params: {}.", JSON.toJSONString(account));
		
		List<WeChartAccount> list = iWeChartAccountService.query(account);
		ResultMap<List<WeChartAccount>> resultMap = ResultMap.success(list);
		logger.info("WeChart account query result: {}.", JSON.toJSONString(resultMap));
		
		return resultMap;
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT)
	public ResultMap<WeChartAccount> update(@RequestBody WeChartAccount account) {
		logger.info("WeChart account update params: {}.", JSON.toJSONString(account));
		
		Boolean result = iWeChartAccountService.update(account);
		ResultMap<WeChartAccount> resultMap = null;
		if (result) {
			resultMap = ResultMap.success(account);
		} else {
			resultMap = ResultMap.fail("Update weChart account fail!");
		}
		logger.info("WeChart account update result: {}.", JSON.toJSONString(resultMap));
		
		return resultMap;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResultMap<WeChartAccount> delete(@PathVariable String id) {
		logger.info("WeChart account delete params: {}.", id);
		
		Boolean result = iWeChartAccountService.delete(id);
		ResultMap<WeChartAccount> resultMap = null;
		if (result) {
			resultMap = ResultMap.success(null);
		} else {
			resultMap = ResultMap.fail("Delete weChart account fail!");
		}
		logger.info("WeChart account delete result: {}.", JSON.toJSONString(resultMap));
		
		return resultMap;
	}
}
