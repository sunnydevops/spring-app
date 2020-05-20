package com.ltfs.ConcentApp.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String getMd5(String input) {
		String data = String.valueOf(input) + "SOULMORTAL";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		byte[] hashInBytes = md.digest(data.getBytes(StandardCharsets.UTF_8));

		StringBuilder sb = new StringBuilder();
		byte b;
		int i;
		byte[] arrayOfByte1;
		for (i = (arrayOfByte1 = hashInBytes).length, b = 0; b < i;) {
			byte b1 = arrayOfByte1[b];
			sb.append(String.format("%02x", new Object[] { Byte.valueOf(b1) }));
			b++;
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getMd5("{'psNo':'1111','password':'tesla'}"));
	}
}
