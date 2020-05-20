package com.ltfs.ConcentApp.utility;

import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Decryptor {
	private static String cipherString = "AES/ECB/PKCS7Padding";
	private static String algo = "AES";

	public static String decrypt(String input, String key) {
		Security.addProvider((Provider) new BouncyCastleProvider());
		try {
			byte[] crypted = null;
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algo);
			Cipher cipher = Cipher.getInstance(cipherString);
			cipher.init(2, skey);
			crypted = cipher.doFinal(DatatypeConverter.parseBase64Binary(input.trim()));
			return new String(crypted);
		} catch (Exception e) {
			System.out.println("Exception in Decryptor.Class:- "+e.getMessage());
			return e.getMessage();
		}
	}

	public static void main(String[] args) {
		String chiperText = "OutqY2WhqPmXUrv2dbxjsq//M5db2LyTzwVfm8q3/Xt4j2ZaV1Ga2LK3PHq5p/vFXJeOV8Vbapagmx8OVSF1kCKSIBvnogP3s3/dYyvY5AQQTzJs8c666vARUW+ZXiVwPgfrT/UJ8lBgA3WoHz/lRnJyYvebJKKAeoXTNTVO2LvWhjuafHPgyTnEKzSYwEj0ZJuM43eamQ+/UNJDCS4X3w==";
		Encryptor.encrypt("Bhupesh", "ytxQz6HJQgHv7Eei");
		System.out.println("Cipher Text: " + chiperText);
		System.out.println("MD5:::" + MD5.getMd5(decrypt(chiperText, "ytxQz6HJQgHv7Eei")));
		System.out.println(decrypt(chiperText, "ytxQz6HJQgHv7Eei"));
	}
}
