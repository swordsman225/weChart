package com.huawei.hicloud.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huawei.hicloud.configuration.wechart.constant.WeChartApiURL;
import com.huawei.hicloud.po.AccessToken;
import com.huawei.hicloud.po.menu.Button;
import com.huawei.hicloud.service.IMenuService;
import com.huawei.hicloud.service.ITokenService;
import com.huawei.hicloud.utils.HttpClientUtils;
import com.huawei.hicloud.vo.ResultMap;

@Service
public class MenuServiceImpl implements IMenuService {

	@Autowired
	private ITokenService iTokenService;
	
	@Override
	public ResultMap<Object> create(Map<String, List<Button>> menuParams) {
		
		AccessToken accessToken = null;
		ResultMap<AccessToken> resultMap = iTokenService.getAccessTokenFromCache();
		if (resultMap.isSuccess()) {
			accessToken = resultMap.getData();
		}
		
		JSONObject json = HttpClientUtils.post(WeChartApiURL.MENU_CREATE + "?access_token=" + accessToken.getAccess_token(), menuParams);
		if (json.getInteger("errcode") == 0) {
			return ResultMap.success(null);
		} else {
			return ResultMap.build(json.getInteger("errcode") + "", json.getString("errmsg"));
		}
	}
	
	@Override
	public ResultMap<Object> get() {
		
		AccessToken accessToken = null;
		ResultMap<AccessToken> resultMap = iTokenService.getAccessTokenFromCache();
		if (!resultMap.isSuccess()) {
			return ResultMap.build((String)resultMap.getCode(), (String)resultMap.getMsg());
		}
		accessToken = resultMap.getData();
		
		JSONObject json = HttpClientUtils.get(WeChartApiURL.MENU_GET + "?access_token=" + accessToken.getAccess_token(), null);
		if (json.getInteger("errcode") == null || json.getInteger("errcode") == 0) {
			return ResultMap.success(json);
		} else {
			return ResultMap.build(json.getInteger("errcode") + "", json.getString("errmsg"));
		}
	}
	
	@Override
	public ResultMap<Object> delete() {
		
		AccessToken accessToken = null;
		ResultMap<AccessToken> resultMap = iTokenService.getAccessTokenFromCache();
		if (!resultMap.isSuccess()) {
			return ResultMap.build((String)resultMap.getCode(), (String)resultMap.getMsg());
		}
		accessToken = resultMap.getData();
		
		JSONObject json = HttpClientUtils.get(WeChartApiURL.MENU_DELETE + "?access_token=" + accessToken.getAccess_token(), null);
		if (json.getInteger("errcode") == null || json.getInteger("errcode") == 0) {
			return ResultMap.success(json);
		} else {
			return ResultMap.build(json.getInteger("errcode") + "", json.getString("errmsg"));
		}
	}

}
