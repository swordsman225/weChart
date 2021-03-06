package com.huawei.hicloud.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.huawei.hicloud.component.wechart.configuration.WeChartProperties;
import com.huawei.hicloud.component.wechart.constant.WeChartApiURL;
import com.huawei.hicloud.dao.IWeChartAccountDao;
import com.huawei.hicloud.po.AccessToken;
import com.huawei.hicloud.po.WeChartAccount;
import com.huawei.hicloud.service.IWeChartService;
import com.huawei.hicloud.utils.EncryptUtils;
import com.huawei.hicloud.utils.HttpClientUtils;
import com.huawei.hicloud.vo.ResultMap;


@Service
public class WeChartServiceImpl implements IWeChartService {

	private static final Logger logger = LoggerFactory.getLogger(WeChartServiceImpl.class);
	
	@Autowired
	private WeChartProperties weChartProperties;
	
	@Autowired
	private IWeChartAccountDao iWeChartAccountDao;

	@Override
	public boolean checkSignature(String appId, String signature, String timestamp, String nonce) {
		
		WeChartAccount weChartAccount = iWeChartAccountDao.findByAppId(appId);
		
		String[] arr = new String[] { timestamp, nonce, weChartAccount.getToken() };

		Arrays.sort(arr);
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : arr) {
			stringBuffer.append(str);
		}

		String sha1Str = EncryptUtils.getSHA1Str(stringBuffer.toString());
		if (!StringUtils.isEmpty(sha1Str)) {
			logger.info("signature: {}, sha1: {}.", signature, sha1Str);
			return sha1Str.equals(signature);
		}
		
		return false;
	}

	@Override
	public ResultMap<AccessToken> getAccessToken() {
		
		HashMap<String, String> params = new HashMap<>();
		params.put("grant_type", "client_credential");
		params.put("appid", weChartProperties.getAppid());
		params.put("secret", weChartProperties.getSecret());
		
		Map<String, Object> map = HttpClientUtils.get(WeChartApiURL.ACCESS_TOKEN, params);
		if (StringUtils.isEmpty(map.get("errcode"))) {
			AccessToken accessToken = JSON.parseObject(JSON.toJSONString(map), AccessToken.class);
			return ResultMap.success(accessToken);
		} else {
			return ResultMap.build((String)map.get("errcode"), (String)map.get("errmsg"));
		}
		
	}

}
