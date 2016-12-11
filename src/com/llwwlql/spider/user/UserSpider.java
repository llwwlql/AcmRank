package com.llwwlql.spider.user;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public interface UserSpider {
	public HttpClient httpClient = new DefaultHttpClient();
	
	/**
	 * doGet()
	 * 根据用户名获取相应的用户信息
	 */
	public void doGet();
}
