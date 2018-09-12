package com.huawei.hicloud;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huawei.hicloud.po.AccessToken;
import com.huawei.hicloud.utils.XmlUtils;
import com.thoughtworks.xstream.XStream;

public class XmlTest {
	
	private static final Logger logger = LoggerFactory.getLogger(XmlTest.class);

	
	@Test
	public void testXml2Json() {
		String xmlStr = "<xml>\r\n" + 
				"	<ToUserName>gh_265859e50ebe</ToUserName>\r\n" + 
				"	<FromUserName>osAH45pN5t9wtbvOPoV7Bxb4OXSo</FromUserName>\r\n" + 
				"	<MsgType>event</MsgType>\r\n" + 
				"	<ScanCodeInfo>\r\n" + 
				"		<ScanType>qrcode</ScanType>\r\n" + 
				"		<ScanResult><![CDATA[http://weixin.qq.com/r/Zh3dxSLEwn_9rWam90h0]]></ScanResult>\r\n" + 
				"	</ScanCodeInfo>\r\n" + 
				"</xml>";
		
		JSONObject xml2JsonT = XmlUtils.xml2Json(xmlStr);
		logger.info(xml2JsonT + "");
	}
	
	
	@Test
	public void textMessageToXml() {

		HashMap<String, Object> obj = new HashMap<>();

		XStream xstream = new XStream();
		xstream.alias("xml", obj.getClass());
		xstream.toXML(obj);

		return;
	}

	@Test
	public void testXStream() {
		AccessToken accessToken = new AccessToken();
		accessToken.setAccess_token(UUID.randomUUID().toString());
		accessToken.setExpires_in(7200L);
		
		String xml = XmlUtils.toXML(accessToken);
		logger.info("xml: {}.", xml);
		
		AccessToken accessToken2 = new AccessToken();
		XmlUtils.fromXML(xml, accessToken2);
		logger.info("accessToken object: {}.", JSON.toJSONString(accessToken2));
		
		/*//XStream xstream = new XStream(new Xpp3Driver(new NoNameCoder()));
		XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xstream.alias("xml", AccessToken.class);// 为包名称重命名
		// 序列化
		String xml = xstream.toXML(accessToken);
		System.out.println(xml);
		// 反序列化
		accessToken = (AccessToken) xstream.fromXML(xml);
		System.out.println(JSON.toJSON(accessToken));*/
	}
	
	@Test
	public void testDom4j() {
		AccessToken accessToken = new AccessToken();
		accessToken.setAccess_token(UUID.randomUUID().toString());
		accessToken.setExpires_in(7200L);
		
		Field[] fields = accessToken.getClass().getDeclaredFields();
		for (Field field : fields) {
			logger.info("field.getName():  {}", field.getName());
		}
		
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("xml");
		rootElement.addElement("name").addText("YJX");
		rootElement.addElement("age").addText("27");
		
		String asXML = document.asXML();
		logger.info("AsXML: {}.", asXML);
		
	}
	
	
	public String toXML(Object obj) {
		if (obj == null) {
			return null;
		}
		
		/*Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("xml");*/
		
		return null;
	}

}
