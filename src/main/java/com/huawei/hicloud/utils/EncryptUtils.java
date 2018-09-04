package com.huawei.hicloud.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encrypt utils.
 * @author JiaxuYan
 *
 */
public class EncryptUtils {

	public static String getSHA1Str(String str){
		if (str == null || str.length() == 0) {
			return null;
		}
		
        String encodeStr = null;
        try {
        	MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encodeStr = new String(EncoderUtils.encodeHex(hash, true));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
	
	
}
