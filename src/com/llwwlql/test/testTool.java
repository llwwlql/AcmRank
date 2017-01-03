package com.llwwlql.test;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.llwwlql.analysis.HduUserAnalysis;
import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestproblem;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.service.QueryResult;
import com.llwwlql.spider.user.HduUserInfo;
import com.llwwlql.tool.*;

public class testTool {
	
	public testTool() {
		// TODO Auto-generated constructor stub
		String path = this.getClass().getClassLoader().getResource(".").getPath();
		System.out.println(path);
	}
	
	public static void main(String[] args) throws IOException {
		testTool test = new testTool();
		
	}
}
