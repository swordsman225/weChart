package com.huawei.hicloud.service.impl;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huawei.hicloud.component.redis.RedisKeyConstants;
import com.huawei.hicloud.component.wechart.configuration.WeChartProperties;
import com.huawei.hicloud.component.wechart.constant.WeChartApiURL;
import com.huawei.hicloud.po.AccessToken;
import com.huawei.hicloud.service.ITokenService;
import com.huawei.hicloud.utils.HttpClientUtils;
import com.huawei.hicloud.vo.ResultMap;


@Service
public class TokenServiceImpl implements ITokenService {

	private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
	
	
	@Autowired
	private WeChartProperties weChartProperties;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	

	/**
	 * 从缓存中获取token.
	 */
	@Override
	public ResultMap<AccessToken> getAccessTokenFromCache(String appId) {
		
		String accessToken = redisTemplate.opsForValue().get(RedisKeyConstants.WX_TOKEN + ":" +appId);
		if (accessToken != null) {
			Long expire = redisTemplate.opsForValue().getOperations().getExpire(RedisKeyConstants.WX_TOKEN);
			AccessToken accessTokenCache = new AccessToken(accessToken, expire);
			logger.info("Get access token form cache.");
			return ResultMap.success(accessTokenCache);
		}
		
		ResultMap<AccessToken> resultMap = getAccessTokenFromWeChart(appId);
		logger.info("Get access token form weChart server: {}.", JSON.toJSON(resultMap.getData()));
		
		return resultMap;
		
	}

	/**
	 * 强制从微信服务器获取token.
	 */
	@Override
	public ResultMap<AccessToken> getAccessTokenFromWeChart(String appId) {
		HashMap<String, String> params = new HashMap<>();
		params.put("grant_type", "client_credential");
		params.put("appid", weChartProperties.getAppid());
		params.put("secret", weChartProperties.getSecret());
		
		JSONObject json = HttpClientUtils.get(WeChartApiURL.ACCESS_TOKEN, params);
		if (StringUtils.isEmpty(json.get("errcode"))) {
			AccessToken accessToken = JSON.parseObject(JSON.toJSONString(json), AccessToken.class);
			redisTemplate.opsForValue().set(RedisKeyConstants.WX_TOKEN + ":" +appId, accessToken.getAccess_token(), 
					accessToken.getExpires_in() > 600 ? accessToken.getExpires_in() - 600 : accessToken.getExpires_in(), TimeUnit.SECONDS);
			logger.info("Get access token success: {}.", JSON.toJSONString(accessToken));
			return ResultMap.success(accessToken);
		} else {
			logger.info("Get access token error, errorcode: {}, errmsg: {}.", json.getString("errcode"), json.getString("errmsg"));
			return ResultMap.build(json.getString("errcode"), json.getString("errmsg"));
		}
	}

}
