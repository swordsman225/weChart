package com.huawei.hicloud.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.huawei.hicloud.po.WeChartAccount;

@Repository
public interface IWeChartAccountDao {
	
	public Integer create(WeChartAccount account);

	public WeChartAccount findByPK(String id);
	
	public WeChartAccount findByAppId(String appId);
	
	public List<WeChartAccount> query(WeChartAccount account);
	
	public Integer update(WeChartAccount account);
	
	public Integer delete(String id);
	
}
