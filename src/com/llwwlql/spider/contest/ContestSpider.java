package com.llwwlql.spider.contest;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public interface ContestSpider{

	public HttpClient httpClient = new DefaultHttpClient();
	public void doGet() throws IOException;
	
	public void doPost() throws IOException;
	
}
