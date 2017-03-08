package com.llwwlql.spider.user;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public interface UserSpider {
	public HttpClient httpClient = HttpClientBuilder.create().build();

	public void doGet();
	
	public void savaUserInfo();
	
	public void savaWarningInfo();
}