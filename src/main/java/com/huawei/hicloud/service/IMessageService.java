package com.huawei.hicloud.service;

import com.huawei.hicloud.po.message.EventMessage;
import com.huawei.hicloud.po.message.TextMessage;

public interface IMessageService {

	public String dealEventMessage(EventMessage rcvMsg);
	
	public String dealTextMessage(TextMessage rcvMsg);
	
}
