package com.llwwlql.spider.contest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.llwwlql.bean.Contest;

public class HduLogin {

	private HttpClient httpClient = new DefaultHttpClient();
	private String loginUrl = "http://acm.hdu.edu.cn/diy/contest_login.php?action=login&cid=";
	private Contest contest = new Contest();
	private HttpHost host = new HttpHost("acm.hdu.edu.cn");
	private String baseUrl = "http://acm.hdu.edu.cn/diy/";
	private String location;

	private void doGet() throws IOException {
		// TODO Auto-generated method stub
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6*1000);
		StringBuffer strResult = new StringBuffer();
		location = "http://acm.hdu.edu.cn/diy/contest_ranklist.php?page=1&cid=" + 30926;
		try {
			HttpGet httpget = new HttpGet(location);
			HttpResponse response = httpClient.execute(host, httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				// »ñÈ¡ÍøÒ³Ô´ÂëÐÅÏ¢
				strResult.append(EntityUtils.toString(entity, "GB2312"));
				System.out.println(strResult.toString());
			} else {
				System.out.println(response.getStatusLine().getStatusCode());
				System.out.println("µÇÂ¼Ê§°Ü£¡");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost() throws IOException {
		// TODO Auto-generated method stub
		// int contestId = contest.getId();
		int contestId = 30926;
		loginUrl = loginUrl + contestId;
		try {
			HttpPost httpost = new HttpPost(loginUrl);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("password", "lduacm"));
			httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset
					.forName("gb2312")));
			HttpResponse response = httpClient.execute(host, httpost);
			if (response.getStatusLine().getStatusCode() == 302) {
				httpost.abort();
				this.doGet();
			} else {
				System.out.println("µÇÂ¼HDUContestÊ§°Ü£¡");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		HduLogin hdu = new HduLogin();
		hdu.doPost();
	}
}
