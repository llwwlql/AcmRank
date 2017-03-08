package com.llwwlql.spider.contest;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.VjudgeContestAnalysis;
import com.llwwlql.bean.Contest;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.Property;
import com.llwwlql.tool.SSLSkip;

public class VjudegeContestInfo implements Runnable{

	private String loginUrl = "https://vjudge.net/contest/data";

	public void doGet() throws IOException {

	}

	/**
	 * 获取Vjudge上的Contest
	 */
	public void doPost() throws IOException {
		try {
			HttpClient httpClient = SSLSkip.enableSSL();
			StringBuffer strResult = new StringBuffer();
			HttpPost post = new HttpPost(loginUrl);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(2000).setConnectTimeout(2000)
					.setConnectionRequestTimeout(2000).build();
			post.setConfig(requestConfig);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			this.getKeyValue(nvp);
			//传入的参数是从VjudgeContest中读取的，若想更改读取参数，到该配置文件中更改
			
			nvp.add(new BasicNameValuePair("title", "LDU"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvp,
					"utf-8");
			post.setEntity(entity);
			try {
				HttpResponse httpResponse = httpClient.execute(post);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity2 = httpResponse.getEntity();
					strResult.append(EntityUtils.toString(entity2));
					VjudgeContestAnalysis pageAnalysis = new VjudgeContestAnalysis();
					pageAnalysis.Get_Info(strResult);
					this.saveContest(pageAnalysis.getContest());
				}
				else
				{
					System.out.println("获取失败！");
				}
			} catch (ClientProtocolException e) {
				System.out.println("网络连接异常！");
				e.printStackTrace();
			} catch (ConnectTimeoutException e) {
				System.out.println("请求VjudgeContest超时！");
			} catch (SocketTimeoutException e) {
				System.out.println("VjudgeContest响应超时！");
			} catch (IOException e) {
				System.out.println("网络连接异常！");
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Post参数的key--value
	 * 
	 * @throws IOException
	 */
	public void getKeyValue(List<NameValuePair> nvp) throws IOException {
		Properties prop = new Properties();
		
		InputStream in = this.getClass().getResourceAsStream("/VjudgeContest.properties");
		prop.load(in);
		Iterator<String> it = prop.stringPropertyNames().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = prop.getProperty(key);
			nvp.add(new BasicNameValuePair(key, value));
		}
		in.close();
	}
	
	public void saveContest(List<Contest> Contest){
		// 判断该元素是否存在，不存在的话保存
		BaseService<Contest> contestService = new BaseService<Contest>();
		for (Contest con : Contest) {
			Map <String,Object> propertyVlaue = new HashMap<String, Object>();
			propertyVlaue.put("orginId", con.getOrginId());
			propertyVlaue.put("origin", (short)2);
			String[] key = Property.getProperty(propertyVlaue);
			Object[] value = Property.getValue(propertyVlaue);
			List<Contest> contest2 = contestService.getByParameters("Contest", key, value, true);
			if(contest2.size()==0)
			{
				contestService.save(con);
			}
		}
	}
	
	public void run() {
		try {
			this.doPost();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		VjudegeContestInfo vj = new VjudegeContestInfo();
		vj.doPost();
	}
}