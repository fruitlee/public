package com.haohiyo.smallfiles.common;

public enum ApiCodeEnum {

	success(0,"请求成功"),
	
	/**需要自定义消息，10001只作常量使用*/
	err_10001(10001,""),
	err_auth_40001(40001,"用户未认证"),
	err_auth_40002(40002,"用户名或密码错误"),
	err_sign_40003(40003,"签名验证失败"),
	err_param_40100(40100,"参数不完整"),
	err_param_40200(40200,"参数包含空值"),
	err_param_40300(40300,"文件大小超过限制"),
	err_param_40400(40400,"参数未达到要求"),
	err_param_40500(40500,"参数不合法"),
	err_param_40501(40501,"密码不合法");
	
	private int code; private String msg;

	private ApiCodeEnum(int key, String msg) {
		this.code = key;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}
