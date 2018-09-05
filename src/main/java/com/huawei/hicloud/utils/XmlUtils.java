package com.huawei.hicloud.utils;

import java.io.ByteArrayInputStream;
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
import com.thoughtworks.xstream.mapper.MapperWrapper;

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
				if (element.elements().size() == 0) {
					jsonObject.put(element.getName(), element.getTextTrim());
				} else {
					List<Element> elements2 = element.elements();
					JSONObject subJson = new JSONObject();
					for (Element element2 : elements2) {
						subJson.put(element2.getName(), element2.getTextTrim());
					}
					jsonObject.put(element.getName(), subJson);
				}
			}
		}

		return jsonObject;
	}
	
	
	public static JSONObject xml2JsonT(String xmlStr) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		Element rootElement = document.getRootElement();
		JSONObject jsonObject = xmlElement2JsonObject(rootElement);
		
		return jsonObject;
	}
	
	
	public static JSONObject xmlElement2JsonObject(Element element) {
		
		if (element == null) {
			return null;
		}
		
		JSONObject resultJson = new JSONObject();
		
		List<Element> childElements = element.elements();
		for (Element childElement : childElements) {
			List<Element> subElements = childElement.elements();
			if (subElements != null && subElements.size() == 0) {
				resultJson.put(childElement.getName(), childElement.getTextTrim());
			} else {
				JSONObject childJson = xmlElement2JsonObject(childElement);
				resultJson.put(childElement.getName(), childJson);
			}
		}
		
		return resultJson;
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
		XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_"))) {
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next){
				return new MapperWrapper(next) {
					@Override
					public boolean shouldSerializeMember(@SuppressWarnings("rawtypes") Class definedIn, String fieldName){
						if (definedIn == Object.class){
							try {
								return this.realClass(fieldName) != null;
							} catch (Exception e){
								return false;
							}
						} else {
							return super.shouldSerializeMember(definedIn, fieldName);
						}
					}
				};
			}

		};
		xStream.alias("xml", obj.getClass());
		
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypes(new Class[] { obj.getClass() });
		
		xStream.fromXML(is, obj);
		
		return obj;
	}
	

}
