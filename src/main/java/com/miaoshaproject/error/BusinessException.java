package com.miaoshaproject.error;

//包装器业务异常类
public class BusinessException extends Exception implements CommonError{

	private CommonError commonError;
	
	//直接接收EmBusinessError的参数构造业务异常
	public BusinessException(CommonError commonError) {
		super();
		this.commonError=commonError;
	}
	
	//接收自定义errMsg构造异常
	public BusinessException(CommonError commonError,String errMsg) {
		super();
		this.commonError=commonError;
		this.commonError.setErrMsg(errMsg);
	}
	
	public int getErrCode() {
		// TODO Auto-generated method stub
		return this.commonError.getErrCode();
	}

	public String getErrMsg() {
		// TODO Auto-generated method stub
		return this.commonError.getErrMsg();
	}

	public CommonError setErrMsg(String errMsg) {
		this.commonError.setErrMsg(errMsg);
		return this;
	}

}
