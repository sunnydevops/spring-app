package com.ltfs.ConcentApp.beans;

public class ConcentAppResBean {

	private String errorCode;

	private String errorDesc;

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
		return "ConcentAppResBean [errorCode=" + errorCode + ", errorDesc=" + errorDesc + "]";
	}

}
