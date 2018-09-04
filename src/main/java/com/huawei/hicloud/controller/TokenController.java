package com.huawei.hicloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.hicloud.po.AccessToken;
import com.huawei.hicloud.service.ITokenService;
import com.huawei.hicloud.vo.ResultMap;

@RestController
@RequestMapping(value = "/wechart")
public class TokenController {
	
private static final Logger logger = LoggerFactory.getLogger(WeChartController.class);
	
	@Autowired
	private ITokenService iTokenService;

	
	@RequestMapping(value="/token", method=RequestMethod.GET)
	public ResultMap<AccessToken> getAccessToken() {
		
		ResultMap<AccessToken> resultMap = iTokenService.getAccessTokenFromCache();
		logger.info("Get access token result: {}.", resultMap);
		
		return resultMap;
	}
	
}
