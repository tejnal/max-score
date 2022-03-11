package com.unity.game.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

public class CommonUtils {
	private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String generateUniqueKeyUsingMessageDigest() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest salt = MessageDigest.getInstance("SHA-256");
		salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
		String digest = bytesToHex(salt.digest());
		return digest;
	}
	
	private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
	
	public static boolean isSessionActive(Date loggedInTime, int sessionTimeoutInMinutes) {
		if(null != loggedInTime && ((new Date()).getTime() - loggedInTime.getTime()) <= (10 * 60 * 1000)) {
			return true;
		}
		return false;
	}
}
