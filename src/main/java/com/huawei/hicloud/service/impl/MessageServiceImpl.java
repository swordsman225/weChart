package com.huawei.hicloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.huawei.hicloud.po.message.EventMessage;
import com.huawei.hicloud.po.message.TextMessage;
import com.huawei.hicloud.po.message.constant.EventType;
import com.huawei.hicloud.po.message.constant.MessageType;
import com.huawei.hicloud.service.IMessageService;
import com.huawei.hicloud.utils.XmlUtils;

@Service
public class MessageServiceImpl implements IMessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Override
	public String dealEventMessage(EventMessage revMsg) {

		if (revMsg == null) {
			return null;
		}

		String ackMsg = null;
		String eventType = revMsg.getEvent();
		switch (eventType) {
		case EventType.CLICK:
			ackMsg = this.dealClickMsg(revMsg);
			break;
		default:
			break;
		}

		return ackMsg;
	}
	
	public String dealClickMsg(EventMessage revMsg) {
		
		String eventType = revMsg.getEvent();
		String eventKey = revMsg.getEventKey();
		
		if (!EventType.CLICK.equals(eventType)) {
			return null;
		}
		
		String ackMsg = null;
		switch(eventKey) {
		case "V1001_TODAY_MUSIC":
			TextMessage textMsg = new TextMessage();
			textMsg.setMsgType(MessageType.TEXT);
			textMsg.setToUserName(revMsg.getFromUserName());
			textMsg.setFromUserName(revMsg.getToUserName());
			textMsg.setCreateTime(System.currentTimeMillis() + "");
			textMsg.setContent("事件-今日歌曲");
			
			ackMsg = XmlUtils.toXML(textMsg);
			break;
		default:
			break;
		}
		
		logger.info("Deal click event ack msg: {}.", ackMsg);
		return ackMsg;
	}

}
