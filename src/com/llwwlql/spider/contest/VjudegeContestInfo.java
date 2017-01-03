package com.llwwlql.spider.contest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.VjudgeContestAnalysis;
import com.llwwlql.bean.Contest;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.Property;
import com.llwwlql.tool.SSLSkip;

public class VjudegeContestInfo implements Runnable,ContestSpider {

	private HttpResponse response;
	private HttpEntity entity;
	//private String search = "http://acm.hdu.edu.cn/diy/contest_search.php?action=go&content=LDU&types=1&page=1";
	private String loginUrl = "https://vjudge.net/contest/data";
	private String password = "lduacm";
	private HttpHost host = new HttpHost("vjudge.net");

	public void doGet() throws IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取Vjudge上的Contest
	 */
	public void doPost() throws IOException {
		// TODO Auto-generated method stub
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6*1000);
		StringBuffer strResult = new StringBuffer();
		SSLSkip.enableSSL(httpClient);
		try {
			HttpPost post = new HttpPost(loginUrl);
			post.getParams().setParameter("http.socket.timeout", 5000);
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
			} catch (ConnectTimeoutException e) {
				// TODO: handle exception
				System.out.println("请求VjudgeContest超时！");
			} catch (SocketTimeoutException e) {
				// TODO: handle exception
				System.out.println("VjudgeContest响应超时！");
			} catch (IOException e) {
				System.out.println("网络连接异常！");
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
			// System.out.println("key:" + key + "\tvalue:"+value);
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
		// TODO Auto-generated method stub
		try {
			this.doPost();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		VjudegeContestInfo vj = new VjudegeContestInfo();
		vj.doPost();
	}
}
