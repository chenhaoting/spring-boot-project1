package com.miaoshaproject.error;

public enum EmBusinessError implements CommonError{
	//通用错误类型10001
	PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
	UNKNOWN_ERR(10002,"未知错误"),
	
	//20000开头为用户信息相关错误
	USER_NOT_EXIST(20001,"用户不存在"),
	USER_LOGIN_FAIL(20002,"手机号或者密码错误"),
	USER_NOT_LOGIN(20003,"用户未登录"),
	
	//30000库存错误
	STOCK_NOT_ENOUGH(30001,"库存不足"),
	
	;
	
	
	
	private EmBusinessError(int errCode,String errMsg) {
		this.errCode=errCode;
		this.errMsg=errMsg;
	}
	
	private int errCode;
	private String errMsg;

	public int getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		
		return this.errMsg;
	}

	public CommonError setErrMsg(String errMsg) {
		this.errMsg=errMsg;
		return this;
	}

}