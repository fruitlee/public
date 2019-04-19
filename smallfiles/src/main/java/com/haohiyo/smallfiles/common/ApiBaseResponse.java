package com.haohiyo.smallfiles.common;

import java.util.HashMap;
import java.util.Map;
/**
 * T为data类型
 * @author zhenghongHYE
 *
 * @param <T> ApiBaseResponse.data类型
 */
public class ApiBaseResponse<T> {

	private int code = -1;
	private String msg = "";
	private T data;
	private Map<String, Object> extraMap = new HashMap<>();
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	/**必须是具体类，因为Object无法做json转换*/
	public void setData(T data) {
		this.data = data;
	}
	/**
	 * setCode和setMsg的简便方法
	 * @param apiCodeEnum
	 */
	public void setByEnum(ApiCodeEnum apiCodeEnum){
		this.code = apiCodeEnum.getCode();
		this.msg = apiCodeEnum.getMsg();
	}
	public Map<String, Object> getExtraMap() {
		return extraMap;
	}
	public void setExtraMap(Map<String, Object> extraMap) {
		this.extraMap = extraMap;
	}
}
