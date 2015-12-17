package com.loonandroid.pc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月8日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class UniqueKey {

	public static String hashUniqueKey(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private static String bytesToHexString(byte[] bytes) {
		// http://stackoverflow.com/questions/332079
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
}
