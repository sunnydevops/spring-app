package com.ltfs.ConcentApp.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.ltfs.ConcentApp.DbCon.DBConn;
import com.ltfs.ConcentApp.beans.ConcentAppReqBean;
import com.ltfs.ConcentApp.beans.ConcentAppResBean;
import com.ltfs.ConcentApp.utility.ConcentAppUtility;
import com.ltfs.ConcentApp.utility.DateUtility;
import com.ltfs.ConcentApp.utility.Encryptor;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConcentAppController {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	@Value("${selectCaptchaQuery}")
	private String selectCaptchaQuery;
	
	@Value("${insertCaptchaQuery}")
	private String insertCaptchaQuery;


	@Value("${loggingUser_TBL_CONS_APP_REQ}")
	private String loggingQuery;
	
	@Value("${loggingUpdate_TBL_CONS_APP_REQ}")
	private String queryLoggingUpdate;

	@Value("${updateCaptcha}")
	private String updateCaptchaQuery;
	
	
	
	@Autowired
	Gson gson;

	@GetMapping("/")
	public String home() {

		//System.out.println(selectCaptchaQuery);
		//System.out.println(insertCaptchaQuery);

		//System.out.println(loggingQuery);
		//System.out.println(queryLoggingUpdate);

		//System.out.println(updateCaptchaQuery);

		return "Welcome to ConcentApp";
	}
	
	@RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "/Ad_ConcentApp")
	@ResponseBody
	public String getAuthConcent(@RequestHeader(value = "hashed") String hashed,
			@RequestParam(value = "data") String data) {

		System.out.println(">>>>>>>>>>>>>>>>>> Start API Ad_ConcentApp >>>>>>>>>>>>>>>> ");

		ConcentAppResBean concentAppRes = new ConcentAppResBean();
		ConcentAppReqBean concentAppReqBean = new ConcentAppReqBean();

		if (StringUtils.isNotBlank(hashed) && StringUtils.isNotBlank(data)) {

			concentAppReqBean = ConcentAppUtility.getCheckEncryption(hashed, data);

					System.out.println(" Payload after Decryption ");
					System.out.println(concentAppReqBean.toString());
					
			if (concentAppReqBean.getErrorCode().equals("200")) {

				String chepcha = checkChepchaVarify(concentAppReqBean.getDummy1());

				//if (StringUtils.isNotBlank(chepcha) || !chepcha.equalsIgnoreCase("NA")){
				
					if (!chepcha.equalsIgnoreCase("N")) {

						int i=ConcentAppUtility.logging(concentAppReqBean, concentAppRes, this.jdbcTemplate,loggingQuery);
	
						concentAppRes = DBConn.getConcentAppData(concentAppReqBean);
						// VALID
						ConcentAppUtility.loggingUpdate(concentAppReqBean, concentAppRes, this.jdbcTemplate,queryLoggingUpdate);
	
						ConcentAppUtility.updateCapcha(concentAppReqBean, this.jdbcTemplate,updateCaptchaQuery);

				} else {
					concentAppRes.setErrorCode("500");
					concentAppRes.setErrorDesc("Invalid CAPTCHA");
				}

			} else {
				concentAppRes.setErrorCode("500");
				concentAppRes.setErrorDesc(concentAppReqBean.getErrorDesc());
			}
		} else {
			concentAppRes.setErrorCode("500");
			concentAppRes.setErrorDesc("Empty Input");
		}

System.out.println(">>>>>>>>>>>>>>>>>> END API >>>>>>>>>>>>>>>> "+gson.toJson(concentAppRes));
		
		String encryptedStr = Encryptor.encrypt(gson.toJson(concentAppRes), "ytxQz6HJQgHv7Eei");

		return encryptedStr;

	}

@GetMapping("/getCaptchaCode")
	public String data() {
		Timestamp datetime = DateUtility.getCurrentDate();
		
		String value = ConcentAppUtility.generateCaptchaString();
		

		//String insertCaptchaQuery = "insert into Tbl_CAPTCHA (CAPTCHA_VALUE,C_DATE,FLAG) values (?,?,?)";
		try {
			int j = this.jdbcTemplate.update(insertCaptchaQuery, new Object[] { value, datetime,"Y"});
			System.out.println("Captcha " + value);

		} catch (Exception e) {
			System.out.println("Exception While CaptchaCode " + e.getMessage());
		}
		// String encryptedStr = Encryptor.encrypt(gson.toJson(query),
		// "ytxQz6HJQgHv7Eei");
		return value;

	}



@GetMapping("/checkCaptcha")
	public String checkChepchaVarify(String varifyChepcha) {

		String isValid = "N";

		//String selectCaptchaQuery = "select CAPTCHA_VALUE from Tbl_CAPTCHA where CAPTCHA_VALUE=? AND FLAG=?";
		try {
			List<Map<String, Object>> listdata = this.jdbcTemplate.queryForList(selectCaptchaQuery,
					new Object[] { varifyChepcha, "Y" });

			if (!listdata.isEmpty()) {

				for (Map<String, Object> dta : listdata) {

					isValid = nullToString(dta.get("CAPTCHA_VALUE").toString());
				}

				System.out.println(" CAPCHA from APP " + varifyChepcha + " CAPCHA from Service " + isValid);

				if (varifyChepcha.equals(isValid))
					isValid = "Y";
				else
					isValid = "N";

			} else {
				isValid = "N";
			}

		} catch (Exception e) {
			e.printStackTrace();
			isValid = "N";
			System.out.println("Exception While CaptchaCode Sending CAPCHA AS N " + e.getMessage());

		}
		System.out.println(" CAPCHA Status RETURN " + isValid);
		return isValid;

	}
	

/*	
	
@GetMapping("/checkCaptcha")
public String checkChepchaVarify(@RequestParam(value = "varifyChepcha") String varifyChepcha ) {

	String isValid = "N";
	
	String query = "select CAPTCHA_VALUE from Tbl_CAPTCHA where CAPTCHA_VALUE=? AND FLAG=?";
	try {

		List<Map<String, Object>> listdata = this.jdbcTemplate.queryForList(query, new Object[] { varifyChepcha ,"Y"});

			if (!listdata.isEmpty()) {

				for (Map<String, Object> dta : listdata) {

					isValid = nullToString(dta.get("CAPTCHA_VALUE").toString());
				}

				System.out.println(" CAPCHA from APP "+varifyChepcha+" CAPCHA from Service "+isValid);
				
				if (varifyChepcha.equals(isValid))
					isValid = "Y";
				else
					isValid = "N";
			}
		//M9q8yvz
		//so9yTfUT
		//mDrG4F4
		//6CniNiBH
		//sgkY0WPY7
		
		//a03sw3ys
		//RCZPhnb
		System.out.println(isValid);
		
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Exception While CaptchaCode " + e.getMessage());
		isValid="N";
	}
	return isValid;

}
	
	*/

public static void main(String[] args) {
	
	ConcentAppController c=new ConcentAppController();
	System.out.println(c.checkChepchaVarify("mDrG4F4"));
	
}
public static String nullToString(Object value) {
	return (value == null ? "" : value.toString());
}


}
