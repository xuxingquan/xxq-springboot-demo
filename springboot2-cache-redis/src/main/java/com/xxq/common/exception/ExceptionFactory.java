package com.xxq.common.exception;

import org.apache.commons.lang3.StringUtils;

public class ExceptionFactory {
	
	public static BizFailException create(String errorCode){
		return new BizFailException(errorCode, StringUtils.EMPTY);
	}
	
	public static BizFailException create(String errorCode,String errorMsg){
		return new BizFailException(errorCode,errorMsg);
	}
	
	public static void throwException(String errorCode) throws BizFailException{
		throw create(errorCode);
	}
	
	public static void throwException(String errorCode,String errorMsg) throws BizFailException{
		throw create(errorCode,errorMsg);
	}
}