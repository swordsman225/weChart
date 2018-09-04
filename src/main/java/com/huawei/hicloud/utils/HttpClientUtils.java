package com.huawei.hicloud.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	public static JSONObject get(String url, Map<String, String> params) {

		logger.info("Http client do GET request, url: {}, params: {}.", url, params);

		CloseableHttpResponse response = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			if (!ObjectUtils.isEmpty(params)) {
				Set<Entry<String, String>> entrySet = params.entrySet();
				for (Entry<String, String> param : entrySet) {
					uriBuilder.addParameter(param.getKey(), param.getValue());
				}
			}
			HttpGet httpGet = new HttpGet(uriBuilder.build());

			CloseableHttpClient httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String str1 = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);

				logger.info("Get request result: {}.", str1);
				JSONObject jsonObject = JSON.parseObject(str1);
				return jsonObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	public static JSONObject post(String url, Map<String, ?> params) {

		StringEntity stringEntity = new StringEntity(JSON.toJSONString(params), Charset.forName("UTF-8"));
		stringEntity.setContentEncoding("UTF-8");
		stringEntity.setContentType("application/json");
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String str1 = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);

				logger.info("Upload request result: {}.", str1);
				JSONObject jsonObject = JSON.parseObject(str1);
				return jsonObject;
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	public static JSONObject upload(String url, Map<String, String> params, MultipartFile file) {

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		if (file != null && !file.isEmpty()) {
			try {
				ByteArrayBody byteArrayBody = new ByteArrayBody(file.getBytes(), ContentType.MULTIPART_FORM_DATA,
						file.getOriginalFilename());
				builder.addPart("file", byteArrayBody);
			} catch (IOException e) {
				logger.error(e.toString());
				e.printStackTrace();
			}
		}

		if (params != null && params.size() > 0) {
			StringBody stringBody = new StringBody(JSON.toJSONString(params), ContentType.APPLICATION_JSON);
			builder.addPart("data", stringBody);
		}
		HttpEntity httpEntity = builder.build();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(httpEntity);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String str1 = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);

				logger.info("Upload request result: {}.", str1);
				JSONObject jsonObject = JSON.parseObject(str1);
				return jsonObject;
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

}
