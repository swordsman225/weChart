package com.huawei.hicloud.service;

import java.util.List;

import com.huawei.hicloud.po.WeChartAccount;

public interface IWeChartAccountService {

	public Boolean create(WeChartAccount account);

	public WeChartAccount findByPK(String id);
	
	public List<WeChartAccount> query(WeChartAccount account);
	
	public Boolean update(WeChartAccount account);
	
	public Boolean delete(String id);
	
}
