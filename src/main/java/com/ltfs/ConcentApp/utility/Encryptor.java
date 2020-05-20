package com.ltfs.ConcentApp.utility;

import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Encryptor {

	private static String cipherString = "AES/ECB/PKCS7Padding";
	private static String algo = "AES";

	public static String encrypt(String input, String key) {
		Security.addProvider((Provider) new BouncyCastleProvider());
		try {
			byte[] crypted = null;
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algo);
			Cipher cipher = Cipher.getInstance(cipherString);
			cipher.init(1, skey);
			crypted = cipher.doFinal(input.getBytes());
			return DatatypeConverter.printBase64Binary(crypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		String input = "TW_NEW|T11955220418112309|01-JAN-85|641671|45.126.170.5|3aerewr|yes|ABC|||";
		String key = "ytxQz6HJQgHv7Eei";

		System.out.println(encrypt(input, key));

		System.out.println(Decryptor.decrypt("UmlvjBDu4xlO101vYNRC4ENfYIZWpGLmAH3QoHjgIONGiBA+jZ6RxOB+cmzSxp2rd5T+hHim6TdRUsagVaEYXg==", key));
	}
}
