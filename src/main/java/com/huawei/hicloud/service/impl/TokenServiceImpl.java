package com.huawei.hicloud.service.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huawei.hicloud.configuration.wechart.WeChartProperties;
import com.huawei.hicloud.configuration.wechart.constant.WeChartApiURL;
import com.huawei.hicloud.po.AccessToken;
import com.huawei.hicloud.service.ITokenService;
import com.huawei.hicloud.utils.HttpClientUtils;
import com.huawei.hicloud.vo.ResultMap;


@Service
public class TokenServiceImpl implements ITokenService {

	private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
	
	@Autowired
	private WeChartProperties weChartProperties;
	
	private static AccessToken accessTokenCache = null;

	/**
	 * 从缓存中获取token.
	 */
	@Override
	public ResultMap<AccessToken> getAccessTokenFromCache() {
		
		if (accessTokenCache != null) {
			logger.info("Get access token form cache: {}.", accessTokenCache);
			return ResultMap.success(accessTokenCache);
		}
		
		ResultMap<AccessToken> resultMap = getAccessTokenFromWeChart();
		accessTokenCache = resultMap.getData();
		logger.info("Get access token form weChart server: {}.", JSON.toJSON(accessTokenCache));
		
		return resultMap;
		
	}

	/**
	 * 强制从微信服务器获取token.
	 */
	@Override
	public ResultMap<AccessToken> getAccessTokenFromWeChart() {
		HashMap<String, String> params = new HashMap<>();
		params.put("grant_type", "client_credential");
		params.put("appid", weChartProperties.getAppid());
		params.put("secret", weChartProperties.getSecret());
		
		JSONObject json = HttpClientUtils.get(WeChartApiURL.ACCESS_TOKEN, params);
		if (StringUtils.isEmpty(json.get("errcode"))) {
			AccessToken accessToken = JSON.parseObject(JSON.toJSONString(json), AccessToken.class);
			logger.info("Get access token success: {}.", JSON.toJSONString(accessToken));
			return ResultMap.success(accessToken);
		} else {
			logger.info("Get access token error, errorcode: {}, errmsg: {}.", json.getString("errcode"), json.getString("errmsg"));
			return ResultMap.build(json.getString("errcode"), json.getString("errmsg"));
		}
	}

}
