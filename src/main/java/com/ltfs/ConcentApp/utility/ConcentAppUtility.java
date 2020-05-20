package com.ltfs.ConcentApp.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.jdbc.core.JdbcTemplate;
import com.ltfs.ConcentApp.beans.ConcentAppReqBean;
import com.ltfs.ConcentApp.beans.ConcentAppResBean;
public class ConcentAppUtility {


	public static String nullToString(Object value) {
		return (value == null ? "" : value.toString());
	}

	public static ConcentAppReqBean getCheckEncryption(String hashed, String data) {

		String plainText = "";
		String decodedData = "";

		ConcentAppReqBean concentAppBean = new ConcentAppReqBean();

		try {
			decodedData = ConcentAppUtility.decode(data);

			plainText = Decryptor.decrypt(decodedData, "ytxQz6HJQgHv7Eei");
			System.out.println(" Decrypted plainText " + plainText);

			String hashNew = MD5.getMd5(plainText);

			System.out.println("HashFromApp " + hashed + "  HashFromServer " + hashNew);
			if (!hashNew.equals(hashed)) {
				System.out.println(" Hash Not Match ");

				String prams[] = plainText.split("\\|");

				System.out.println(prams.length);

				int length = prams.length;

				String product = prams[0] != null ? prams[0].trim() : "";
				concentAppBean.setProduct(product);

				String lan = prams[1] != null ? prams[1].trim() : "";
				concentAppBean.setLan_No(lan);

				String dob = prams[2] != null ? prams[2].trim() : "";

				String[] ch = dob.split("-");

				System.out.println("DOB " + dob + " DOB Length " + ch.length);

				if ((ch[0].length() == 2) && (ch[1].length() == 3) && (ch[2].length() == 4)) {

					System.out.println(" DOB Correct length ");
					concentAppBean.setDob(dob);

					String pincode = prams[3] != null ? prams[3].trim() : "";
					concentAppBean.setPincode(pincode);

					String ip = prams[4] != null ? prams[4].trim() : "";
					concentAppBean.setIp(ip);

					String capcha = prams[5] != null ? prams[5].trim() : "";
					concentAppBean.setDummy1(capcha);

					String tnc = prams[6] != null ? prams[6].trim() : "";
					concentAppBean.setDummy2(tnc);

					String customerName = prams[7] != null ? prams[7].trim() : "";
					concentAppBean.setDummy3(customerName);

					if (length > 8) {

						String mobile = prams[8] != null ? prams[8].trim() : "";
						concentAppBean.setDummy4(mobile);
					} else {

						concentAppBean.setDummy4("NA");
					}

					concentAppBean.setErrorCode("500");
					concentAppBean.setErrorDesc("FAIL");
					System.out.println("Hash NOT Matched");

					System.out.println(concentAppBean.toString());
					return concentAppBean;

				} else {
					// Please Enter DOB in "DD-MMM-YYYY" format .

					concentAppBean.setErrorCode("500");
					concentAppBean.setErrorDesc("Please Enter DOB in \"DD-MMM-YYYY\" format .");

					return concentAppBean;

				}

			} else {
				// PRODUCT|LAN_NO|DOB|PINCODE|IP|DUMMY1|DUMMY2|DUMMY3|DUMMY4

				String prams[] = plainText.split("\\|");

				System.out.println(prams.length);

				int length = prams.length;

				String product = prams[0] != null ? prams[0].trim() : "";
				concentAppBean.setProduct(product);

				String lan = prams[1] != null ? prams[1].trim() : "";
				concentAppBean.setLan_No(lan);

				String dob = prams[2] != null ? prams[2].trim() : "";

				String[] ch = dob.split("-");

				System.out.println("DOB " + dob + " DOB Length " + ch.length);

				if ((ch[0].length() == 2) && (ch[1].length() == 3) && (ch[2].length() == 4)) {

					System.out.println(" DOB Correct length ");
					concentAppBean.setDob(dob);

					String pincode = prams[3] != null ? prams[3].trim() : "";
					concentAppBean.setPincode(pincode);

					String ip = prams[4] != null ? prams[4].trim() : "";
					concentAppBean.setIp(ip);

					String capcha = prams[5] != null ? prams[5].trim() : "";
					concentAppBean.setDummy1(capcha);

					String tnc = prams[6] != null ? prams[6].trim() : "";
					concentAppBean.setDummy2(tnc);

					String customerName = prams[7] != null ? prams[7].trim() : "";
					concentAppBean.setDummy3(customerName);

					if (length > 8) {

						String mobile = prams[8] != null ? prams[8].trim() : "";
						concentAppBean.setDummy4(mobile);
					} else {

						concentAppBean.setDummy4("NA");
					}

					concentAppBean.setErrorCode("200");
					concentAppBean.setErrorDesc("SUC");
					System.out.println("Hash Matched");

					System.out.println(concentAppBean.toString());
					return concentAppBean;

				} else {
					// Please Enter DOB in "DD-MMM-YYYY" format .
					concentAppBean.setErrorCode("500");
					concentAppBean.setErrorDesc("Please Enter DOB in \"DD-MMM-YYYY\" format .");

					return concentAppBean;

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception In While Encryption and Hashing check " + e.getMessage());
			concentAppBean.setErrorCode("500");
			concentAppBean.setErrorDesc("FAIL");

			return concentAppBean;

		}
	}

	public static String decode(String data) {
		String decodedData = "";
		try {
			byte[] valueDecoded = Base64.decodeBase64(data);
			decodedData = new String(valueDecoded);

			System.out.println("-------Decode Done----------");
		} catch (Exception e) {
			System.out.println("Exception While Decoding " + e.getMessage() + " InputData := " + data);
		}
		return decodedData;
	}

	// XwsSFOc/3kv3vfHLxJ4ApAaeisHYMHIN6SjMJYjM+BrSXNaMDJ6HoxsgqjuWeGmTnz+/7B9hqY3x1lUBjIxBoPIOPSq3CZkQv12FmH4Vo0s=

	/*
	 * public static String encode(String data) { String decodedData = "";
	 * 
	 * String a=
	 * "XwsSFOc/3kv3vfHLxJ4ApAaeisHYMHIN6SjMJYjM+BrSXNaMDJ6HoxsgqjuWeGmTnz+/7B9hqY3x1lUBjIxBoPIOPSq3CZkQv12FmH4Vo0s=";
	 * 
	 * byte b[]=new byte[a];
	 * 
	 * try { byte[] valueDecoded = Base64.encodeBase64(b); decodedData = new
	 * String(valueDecoded); } catch (Exception e) {
	 * System.out.println("Exception While Decoding " + e.getMessage() +
	 * " InputData := " + data); } return decodedData; }
	 */

	public static int logging(ConcentAppReqBean concentAppBean, ConcentAppResBean concentAppRes,
			JdbcTemplate jdbcTemplate,String loggingQuery) {

		int j = 0;

		try {

			//String loggingQuery = "insert into TBL_CONS_APP_REQ (LAN_NO,PRODUCT,DOB,PINCODE,MOBILE,IP,ERR_CODE,ERR_DESC,C_DT,M_DT,CUSTOMER_NAME)"
			//		+ "values (?,?,?,?,?,?,?,?,?,?,?)";
			
			j = jdbcTemplate.update(loggingQuery,
					new Object[] { concentAppBean.getLan_No(), concentAppBean.getProduct(), concentAppBean.getDob(),
							concentAppBean.getPincode(), concentAppBean.getDummy4(), concentAppBean.getIp(),
							concentAppBean.getErrorCode(), concentAppBean.getErrorDesc(), DateUtility.getCurrentDate(),
							"", concentAppBean.getDummy3() });

			System.out.println("Logging Done " + j);

		} catch (Exception e) {
			System.out.println(">>>>>> Exception While Insert Logs " + e.getMessage());
			e.printStackTrace();
		}

		return j;
	}

	public static int loggingUpdate(ConcentAppReqBean concentAppBean, ConcentAppResBean concentAppRes,
			JdbcTemplate jdbcTemplate,String queryLoggingUpdate) {
		int j = 0;
		try {

//			String queryLoggingUpdate = "update TBL_CONS_APP_REQ set ERR_CODE=?,ERR_DESC=?,M_DT=? where LAN_NO=?";

			j = jdbcTemplate.update(queryLoggingUpdate, new Object[] { concentAppRes.getErrorCode(), concentAppRes.getErrorDesc(),
					DateUtility.getCurrentDate(), concentAppBean.getLan_No() });

			System.out.println("Logging Done " + j);
		} catch (Exception e) {
			System.out.println(">>>>>> Exception While Updating Logs " + e.getMessage());
			e.printStackTrace();
		}
		return j;
	}

	/*
	 * public static String generateCaptchaString() { Random random = new Random();
	 * int length = 7 + (Math.abs(random.nextInt()) % 3);
	 * 
	 * StringBuffer captchaStringBuffer = new StringBuffer(); for (int i = 0; i <
	 * length; i++) { int baseCharNumber = Math.abs(random.nextInt()) % 62; int
	 * charNumber = 0; if (baseCharNumber < 26) { charNumber = 65 + baseCharNumber;
	 * } else if (baseCharNumber < 52) { charNumber = 97 + (baseCharNumber - 26); }
	 * else { charNumber = 48 + (baseCharNumber - 52); }
	 * captchaStringBuffer.append((char) charNumber); }
	 * 
	 * return captchaStringBuffer.toString(); }
	 * 
	 * 
	 * 
	 */

	public static String generateCaptchaString() {
		int aNumber = 0;
		aNumber = (int) ((Math.random() * 9000000) + 1000000);
		String captchaValue = "" + aNumber;
		// System.out.print((captchaValue));

		return captchaValue;
	}

	public static void updateCapcha(ConcentAppReqBean concentAppBean, JdbcTemplate jdbcTemplate,String updateCaptchaQuery) {
		try {
			
			//String query =updateCaptchaQuery;
			//String updateCaptchaQuery = "update Tbl_CAPTCHA set FLAG=? where CAPTCHA_VALUE=?";
			int j = jdbcTemplate.update(updateCaptchaQuery, new Object[] { "N", concentAppBean.getDummy1() });

			System.out.println("Capcha UpDated " + j);
		} catch (Exception e) {
			System.out.println("Exception While updateCapcha " + e.getMessage());

		}
	}

	public static void main(String[] args) {

		
		
		ConcentAppUtility ConcentAppUtility=new ConcentAppUtility();

		
		
		
		
		ConcentAppReqBean concentAppBean = new ConcentAppReqBean();

		String plainText = "TL|3946319|17-NOV-1996|415525|106.210.239.188|8500621|yes|Amol bodare|";

		String plainText2 = "TL|3946319|17-NOV-1996|415525|106.210.239.188|8500621|yes|Amol bodare||||";

		String plainText3 = "TL|3946319|17-NOV-1996|415525|106.210.239.188|8500621|yes|Amol bodare|75855555555";

		String prams[] = plainText.split("\\|");
		System.out.println(prams.length);

		int length = prams.length;

		String product = prams[0] != null ? prams[0].trim() : "";
		concentAppBean.setProduct(product);

		String lan = prams[1] != null ? prams[1].trim() : "";
		concentAppBean.setLan_No(lan);

		String dob = prams[2] != null ? prams[2].trim() : "";

		String[] ch = dob.split("-");

		System.out.println("DOB " + dob + " DOB Length " + ch.length);

		if ((ch[0].length() == 2) && (ch[1].length() == 3) && (ch[2].length() == 4)) {

			System.out.println(" DOB Correct length ");
			concentAppBean.setDob(dob);

			String pincode = prams[3] != null ? prams[3].trim() : "";
			concentAppBean.setPincode(pincode);

			String ip = prams[4] != null ? prams[4].trim() : "";
			concentAppBean.setIp(ip);

			String capcha = prams[5] != null ? prams[5].trim() : "";
			concentAppBean.setDummy1(capcha);

			String tnc = prams[6] != null ? prams[6].trim() : "";
			concentAppBean.setDummy2(tnc);

			String customerName = prams[7] != null ? prams[7].trim() : "";
			concentAppBean.setDummy3(customerName);

			if (length > 8) {

				String mobile = prams[8] != null ? prams[8].trim() : "";
				concentAppBean.setDummy4(mobile);
			} else {

				concentAppBean.setDummy4("NA");
			}

			System.out.println(concentAppBean.toString());

		}

		System.out.println(generateCaptchaString());
		String a = "Please Enter DOB in \"DD-MMM-YYYY\" format .";

		System.out.println(a);

		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < 11; i++) {
			list.add(new Integer(i));
		}
		Collections.shuffle(list);

		for (int i = 0; i < 6; i++) {
			System.out.print(list.get(i));
		}
	}

}
