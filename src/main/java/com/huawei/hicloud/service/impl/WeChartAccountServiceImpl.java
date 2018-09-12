package com.huawei.hicloud.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.hicloud.dao.IWeChartAccountDao;
import com.huawei.hicloud.po.WeChartAccount;
import com.huawei.hicloud.service.IWeChartAccountService;

@Service
public class WeChartAccountServiceImpl implements IWeChartAccountService {

	private static final Logger logger = LoggerFactory.getLogger(WeChartAccountServiceImpl.class);
	
	@Autowired
	private IWeChartAccountDao iWeChartAccountDao;
	
	@Override
	public Boolean create(WeChartAccount account) {
		Integer result = iWeChartAccountDao.create(account);
		if (result > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public WeChartAccount findByPK(String id) {
		WeChartAccount account = iWeChartAccountDao.findByPK(id);
		
		return account;
	}

	@Override
	public List<WeChartAccount> query(WeChartAccount account) {
		List<WeChartAccount> list = iWeChartAccountDao.query(account);
		
		return list;
	}

	@Override
	public Boolean update(WeChartAccount account) {
		Integer result = iWeChartAccountDao.update(account);
		if (result > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public Boolean delete(String id) {
		Integer result = iWeChartAccountDao.delete(id);
		if (result > 0) {
			return true;
		}
		logger.info("Delete wechart account fail! id: {}.", id);
		
		return false;
	}

}
