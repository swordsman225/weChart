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
import com.huawei.hicloud.po.message.EventMessage;
import com.huawei.hicloud.po.message.TextMessage;
import com.huawei.hicloud.po.message.constant.MessageType;
import com.huawei.hicloud.service.IMessageService;
import com.huawei.hicloud.service.IWeChartService;
import com.huawei.hicloud.utils.XmlUtils;

@RestController
@RequestMapping(value = "/wechart")
public class WeChartController {

	private static final Logger logger = LoggerFactory.getLogger(WeChartController.class);
	
	@Autowired
	private IWeChartService iWeChartService;
	
	@Autowired
	private IMessageService iMessageService;
	
	@RequestMapping(value = "/handler", method = RequestMethod.GET)
	public String checkSignature(@RequestParam String appid, @RequestParam String signature, @RequestParam String timestamp, 
			@RequestParam String nonce, @RequestParam String echostr) {
		logger.info("WeChart server token authentication request params, appid:{}, signature: {}, timestamp: {}, nonce: {}, echostr: {}.",
				appid, signature, timestamp, nonce, echostr);
		
		boolean tokenAuthResult = iWeChartService.checkSignature(appid, signature, timestamp, nonce);
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
		
		String ackMsg = null;
		switch (msgType) {
		case MessageType.EVENT:
			EventMessage eventMessage = jsonObject.toJavaObject(EventMessage.class);
			ackMsg = iMessageService.dealEventMessage(eventMessage);
			break;
		case MessageType.TEXT:
			TextMessage textMessage = jsonObject.toJavaObject(TextMessage.class);
			ackMsg = iMessageService.dealTextMessage(textMessage);
			break;
		default:
			break;
		}
		
		logger.info("WeChart server message handle result: {}.", ackMsg);
		return ackMsg;
	}
	
}
