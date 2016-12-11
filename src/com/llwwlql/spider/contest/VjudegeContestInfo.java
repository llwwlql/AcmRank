package com.llwwlql.spider.contest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.llwwlql.tool.SSLSkip;

public class VjudegeContestInfo implements ContestSpider {

	private HttpResponse response;
	private HttpEntity entity;
	private String search = "http://acm.hdu.edu.cn/diy/contest_search.php?action=go&content=LDU&types=1&page=1";
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
		SSLSkip ssl = new SSLSkip();
		ssl.enableSSL(httpClient);
		HttpPost post = new HttpPost(loginUrl);
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		this.getKeyValue(nvp);
		nvp.add(new BasicNameValuePair("title", "LDU"));
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvp,
					"utf-8");
			post.setEntity(entity);
			try {
				HttpResponse httpResponse = httpClient.execute(post);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity2 = httpResponse.getEntity();
					strResult.append(EntityUtils.toString(entity2));
					System.out.println(strResult);
				}
				else
				{
					System.out.println("获取失败！");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
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
		InputStream in = new BufferedInputStream(new FileInputStream(
				"../AcmRank/src/VjudgeContest.properties"));
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

	public static void main(String[] args) throws IOException {
		VjudegeContestInfo vj = new VjudegeContestInfo();
		vj.doPost();	
	}
}
