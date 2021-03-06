package com.huawei.hicloud.utils;

import com.huawei.hicloud.po.message.Message;

public class MessageUtils {

	/**
	 * Acknowledge received message.
	 * @param rcvMsg
	 * @param ackMsg
	 */
	public static void ackMessage(Message rcvMsg, Message ackMsg) {
		if (rcvMsg == null || ackMsg == null) {
			return ;
		}
		
		ackMsg.setFromUserName(rcvMsg.getToUserName());
		ackMsg.setToUserName(rcvMsg.getFromUserName());
		ackMsg.setCreateTime(System.currentTimeMillis());
	}
	
}
