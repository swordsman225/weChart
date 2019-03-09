package com.huawei.hicloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.huawei.hicloud.po.message.EventMessage;
import com.huawei.hicloud.po.message.TextMessage;
import com.huawei.hicloud.po.message.constant.EventType;
import com.huawei.hicloud.po.message.constant.MessageType;
import com.huawei.hicloud.service.IMessageService;
import com.huawei.hicloud.utils.MessageUtils;
import com.huawei.hicloud.utils.XmlUtils;

@Service
public class MessageServiceImpl implements IMessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	/**
	 * Deal event message.
	 */
	@Override
	public String dealEventMessage(EventMessage rcvMsg) {
		if (rcvMsg == null || !MessageType.EVENT.equals(rcvMsg.getMsgType())) {
			logger.info("rcvMsg is null or message type doesn't match!");
			return null;
		}

		String ackMsg = null;
		String eventType = rcvMsg.getEvent();
		logger.info("Event type is {}.", eventType);
		switch (eventType) {
		case EventType.CLICK:
			ackMsg = this.dealClickMsg(rcvMsg);
			break;
		case EventType.VIEW:
			break;
		case EventType.SCANCODE_PUSH:
			TextMessage textMsg = new TextMessage();
			MessageUtils.ackMessage(rcvMsg, textMsg);
			textMsg.setContent("扫码事件");
			ackMsg = XmlUtils.toXML(textMsg);
			break;
		case EventType.SCANCODE_WAITMSG:
			TextMessage textMsg2 = new TextMessage();
			MessageUtils.ackMessage(rcvMsg, textMsg2);
			textMsg2.setContent("Scan QR code result: " + JSON.toJSONString(rcvMsg.getScanCodeInfo()) + ".");
			ackMsg = XmlUtils.toXML(textMsg2);
			break;
		case EventType.SUBSCRIBE:
			TextMessage textMsg3 = new TextMessage();
			MessageUtils.ackMessage(rcvMsg, textMsg3);
			textMsg3.setContent("<a href=\"http://www.baidu.com\">百度</a>");
			ackMsg = XmlUtils.toXML(textMsg3);
			break;
		default:
			break;
		}

		return ackMsg;
	}
	
	/**
	 * Deal text message.
	 */
	@Override
	public String dealTextMessage(TextMessage rcvMsg) {
		if (rcvMsg == null) {
			return null;
		}
		String content = rcvMsg.getContent();
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		
		String ackMsg = null;
		
		TextMessage textMsg = new TextMessage();
		MessageUtils.ackMessage(rcvMsg, textMsg);
		if ("225".equals(content)) {
			textMsg.setContent("This is my birthday!");
			ackMsg = XmlUtils.toXML(textMsg);
		} else {
			textMsg.setContent("文本事件-" + content);
			ackMsg = XmlUtils.toXML(textMsg);
		}
		
		return ackMsg;
	}


	/**
	 * 
	 * @param rcvMsg
	 * @return
	 */
	public String dealClickMsg(EventMessage rcvMsg) {
		
		String eventType = rcvMsg.getEvent();
		String eventKey = rcvMsg.getEventKey();
		
		if (!EventType.CLICK.equals(eventType)) {
			return null;
		}
		
		String ackMsg = null;
		switch(eventKey) {
		case "V1001_TODAY_MUSIC":
			TextMessage textMsg = new TextMessage();
			MessageUtils.ackMessage(rcvMsg, textMsg);
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
