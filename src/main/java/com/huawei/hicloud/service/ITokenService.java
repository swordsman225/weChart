package com.huawei.hicloud.service;

import com.huawei.hicloud.po.AccessToken;
import com.huawei.hicloud.vo.ResultMap;

public interface ITokenService {

	public ResultMap<AccessToken> getAccessTokenFromCache();
	
	public ResultMap<AccessToken> getAccessTokenFromWeChart();
	
}
