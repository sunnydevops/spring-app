package com.ltfs.ConcentApp.beans;

public class ConcentAppReqBean {

	private String product;
	private String lan_No;
	private String dob;
	private String pincode;
	private String ip;
	private String errorCode;
	private String errorDesc;
	private String dummy1;
	private String dummy2;
	private String dummy3;
	private String dummy4;
	
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getLan_No() {
		return lan_No;
	}

	public void setLan_No(String lan_No) {
		this.lan_No = lan_No;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDummy1() {
		return dummy1;
	}

	public void setDummy1(String dummy1) {
		this.dummy1 = dummy1;
	}

	public String getDummy2() {
		return dummy2;
	}

	public void setDummy2(String dummy2) {
		this.dummy2 = dummy2;
	}

	public String getDummy3() {
		return dummy3;
	}

	public void setDummy3(String dummy3) {
		this.dummy3 = dummy3;
	}

	public String getDummy4() {
		return dummy4;
	}

	public void setDummy4(String dummy4) {
		this.dummy4 = dummy4;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	@Override
	public String toString() {
		return "ConcentAppReqBean [product=" + product + ", lan_No=" + lan_No + ", dob=" + dob + ", pincode=" + pincode
				+ ", ip=" + ip + ", errorCode=" + errorCode + ", errorDesc=" + errorDesc + ", dummy1=" + dummy1
				+ ", dummy2=" + dummy2 + ", dummy3=" + dummy3 + ", dummy4=" + dummy4 + "]";
	}



	

}
