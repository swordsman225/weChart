package com.huawei.hicloud.vo;

import java.util.HashMap;

public class ResultMap<T> extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public static final String CODE_SUCCESS = "200";
	public static final String CODE_SERVER_ERROR = "500";
	public static final String CODE_BAD_REQUEST = "400";

	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_FAIL = "fail";
	public static final String STATUS_ERROR = "error";

	public ResultMap() {
		super();
	}

	public static <T> ResultMap<T> success(T data) {
		ResultMap<T> resultMap = ResultMap.build(data, STATUS_SUCCESS, CODE_SUCCESS, null);
		
		return resultMap;
	}
	
	public static <T> ResultMap<T> fail(String msg) {
		ResultMap<T> resultMap = ResultMap.build(null, STATUS_FAIL, null, msg);
		
		return resultMap;
	}
	
	public static <T> ResultMap<T> error(String msg) {
		ResultMap<T> resultMap = ResultMap.build(null, STATUS_ERROR, CODE_SERVER_ERROR, msg);
		
		return resultMap;
	}
	
	public static <T> ResultMap<T> build(String code, String msg) {
		ResultMap<T> resultMap = ResultMap.build(null, null, code, msg);
		
		return resultMap;
	}
	
	public static <T> ResultMap<T> build(String code, String msg, T data) {
		ResultMap<T> resultMap = ResultMap.build(data, null, code, msg);
		
		return resultMap;
	}
	
	public static <T> ResultMap<T> build(T data, String status, String code, String msg) {
		ResultMap<T> resultMap = new ResultMap<>();
		
		resultMap.setData(data);
		resultMap.setStatus(status);
		resultMap.setCode(code);
		resultMap.setMsg(msg);
		
		return resultMap;
	}
	
	public boolean isSuccess() {
		return CODE_SUCCESS.equals(this.getCode());
	}

	@SuppressWarnings("unchecked")
	public T getData() {
		return (T) this.get("data");
	}

	public void setData(T data) {
		this.put("data", data);
	}

	public Object getCode() {
		return this.get("code");
	}

	public void setCode(String code) {
		this.put("code", code);
	}

	public Object getStatus() {
		return this.get("status");
	}

	public void setStatus(String status) {
		this.put("status", status);
	}

	public Object getMsg() {
		return this.get("msg");
	}

	public void setMsg(String msg) {
		this.put("msg", msg);
	}

}
