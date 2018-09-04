package com.huawei.hicloud.service;

import com.huawei.hicloud.po.message.EventMessage;

public interface IMessageService {

	public String dealEventMessage(EventMessage revMsg);
	
}
