package com.huawei.hicloud.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XmlUtils {

	public static JSONObject xml2Json(InputStream is) {
		if (is == null) {
			return null;
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}

		JSONObject jsonObject = new JSONObject();

		Element rootElement = document.getRootElement();
		List<Element> elements = rootElement.elements();
		if (elements != null && elements.size() > 0) {
			for (Element element : elements) {
				jsonObject.put(element.getName(), element.getTextTrim());
			}
		}

		return jsonObject;
	}

	public static String toXML(Object obj) {
		XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStream.alias("xml", obj.getClass());

		String xml = xStream.toXML(obj);

		return xml;
	}

	public static <T> T fromXML(String xml, T obj) {
		if (xml == null || xml.length() == 0) {
			return null;
		}
		XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStream.alias("xml", obj.getClass());
		
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypes(new Class[] { obj.getClass() });
		
		xStream.fromXML(xml, obj);
		
		return obj;
	}
	
	public static <T> T fromXML(InputStream is, T obj) {
		if (is == null) {
			return null;
		}
		XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStream.alias("xml", obj.getClass());
		
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypes(new Class[] { obj.getClass() });
		
		xStream.fromXML(is, obj);
		
		return obj;
	}
	

}
