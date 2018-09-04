package com.huawei.hicloud.service;

import com.huawei.hicloud.po.AccessToken;
import com.huawei.hicloud.vo.ResultMap;

public interface IWeChartService {

	public boolean checkSignature(String signature, String timestamp, String nonce);

	public ResultMap<AccessToken> getAccessToken();
	
}
