package com.llwwlql.spider.user;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public interface UserSpider {
	public HttpClient httpClient = new DefaultHttpClient();
	
	/**
	 * doGet()
	 * �����û�����ȡ��Ӧ���û���Ϣ
	 */
	public void doGet();
}
