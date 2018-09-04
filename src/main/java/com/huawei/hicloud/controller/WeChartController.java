package com.huawei.hicloud.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huawei.hicloud.configuration.wechart.constant.EventType;
import com.huawei.hicloud.configuration.wechart.constant.MessageType;
import com.huawei.hicloud.po.message.Message;
import com.huawei.hicloud.service.IWeChartService;
import com.huawei.hicloud.utils.XmlUtils;

@RestController
@RequestMapping(value = "/wechart")
public class WeChartController {

	private static final Logger logger = LoggerFactory.getLogger(WeChartController.class);
	
	@Autowired
	private IWeChartService iWeChartService;
	
	@RequestMapping(value = "/handler", method = RequestMethod.GET)
	public String checkSignature(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce,
			@RequestParam String echostr) {
		logger.info("WeChart server token authentication request params, signature: {}, timestamp: {}, nonce: {}, echostr: {}.",
				signature, timestamp, nonce, echostr);
		
		boolean tokenAuthResult = iWeChartService.checkSignature(signature, timestamp, nonce);
		if (tokenAuthResult) {
			logger.info("Token auth success!");
			return echostr;
		}
		
		logger.info("Token auth fail!");
		return null;
	}
	
	@RequestMapping(value = "/handler", method = RequestMethod.POST)
	public String weChartMsgHandler(HttpServletRequest request, @RequestParam Map<String, String> params) throws IOException {
		logger.info("WeChart server message handler request params: {}.", JSON.toJSONString(params));
		
		JSONObject jsonObject = XmlUtils.xml2Json(request.getInputStream());
		logger.info("XML message: {}.", jsonObject);
		
		String msgType = jsonObject.getString("MsgType");
		String event = jsonObject.getString("Event");
		String eventKey = jsonObject.getString("EventKey");
		String toUserName = jsonObject.getString("ToUserName");
		String fromUserName = jsonObject.getString("FromUserName");
		
		switch (msgType) {
		case MessageType.EVENT:
			if (EventType.CLICK.equals(event)) {
				if ("V1001_TODAY_MUSIC".equals(eventKey)) {
					Message message = new Message();
					message.setMsgType(MessageType.TEXT);
					message.setToUserName(fromUserName);
					message.setFromUserName(toUserName);
					message.setCreateTime(System.currentTimeMillis() + "");
					message.setContent("事件-今日歌曲");
					
					return XmlUtils.toXML(message);
				} else if ("ev-111111111111112".equals(eventKey)) {
					Message message = new Message();
					message.setMsgType(MessageType.EVENT);
					message.setEvent(EventType.CLICK);
					message.setToUserName(fromUserName);
					message.setFromUserName(toUserName);
					message.setContent("事件-2");
					
					return XmlUtils.toXML(message);
				}
			}
			break;

		default:
			break;
		}
		
		
		logger.info("WeChart server message handle end!");
		return null;
	}
	
}
