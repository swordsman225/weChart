package com.huawei.hicloud;

import java.security.MessageDigest;
import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.hicloud.utils.EncryptUtils;

public class ShaTest {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ShaTest.class);
	
	@Test
	public void testSHA1() {
		
		String[] arr = new String[] {"1535764207", "1817421686", "hicloud"};
		
		Arrays.sort(arr);
		
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : arr) {
			stringBuffer.append(str);
		}
		
		String originText = stringBuffer.toString();
		
		String sha1 = getSha1(originText);
		
		String sha2 = EncryptUtils.getSHA1Str(originText);
		
		logger.info("sha1: {}, sha2: {}.", sha1, sha2);
		
	}
	
	
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
	
}
