package com.pdsu.csc.bean;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回给前端的数据存储类型
 * @author Admin
 */
@ToString
public class Result{

	/**
	 * 
	 */
	private Map<String, Object> json = new HashMap<>();

	public Map<String, Object> getJson() {
		return json;
	}

	public static Result success() {
		Result result = new Result();
		result.add("msg", "心有所想, 日复一日, 必有精进.");
		result.add("code", 200);
		return result;
	}

	public static Result bedRequest() {
		Result result = new Result();
		result.add("msg", "bed request");
		result.add("code", 400);
		return result;
	}

	public static Result fail() {
		Result result = new Result();
		result.add("code", 500);
		result.add("msg", "大丈夫, どんな未来でも, 太陽は昇るからきっと");
		return result;
	}

	public static Result notFound() {
		Result result = new Result();
		result.add("code", 404);
		result.add("msg", "The page you want was not found");
		return result;
	}

	public static Result accepted() {
		Result result = new Result();
		result.add("code", 202);
		result.add("msg", "Ah summer, what power you have to make us suffer and like it.");
		return result;
	}

	public static Result permission() {
		Result result = new Result();
		result.add("code", 403);
		result.add("msg", "Insufficient permissions");
		return result;
	}

	public static Result permissionByTeacher() {
		Result result = new Result();
		result.add("code", 403);
		result.add("msg", "Insufficient permissions, At least permission is required for the teacher.");
		return result;
	}

	public Result add(String key, Object value) {
		this.json.put(key, value);
		return this;
	}
	
}
