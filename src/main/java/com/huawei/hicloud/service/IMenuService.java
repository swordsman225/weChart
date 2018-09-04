package com.huawei.hicloud.service;

import java.util.List;
import java.util.Map;

import com.huawei.hicloud.po.menu.Button;
import com.huawei.hicloud.vo.ResultMap;

public interface IMenuService {

	public ResultMap<Object> create(Map<String, List<Button>> params);
	
	public ResultMap<Object> get();
	
	public ResultMap<Object> delete();
	
}
