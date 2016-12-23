package com.llwwlql.spider.user;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.VjudgeUserAnalysis;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.User;
import com.llwwlql.bean.Vjudgeuser;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.SSLSkip;
import com.llwwlql.tool.SaveLog;

@Entity
public class VjudegeUserInfo implements UserSpider,Runnable{

	@ManyToOne
	private Vjudgeuser vjudgeUser = null;
	private String url = "https://vjudge.net/user/data";
	private VjudgeUserAnalysis pageAnalysis = new VjudgeUserAnalysis();
	private User user;
	
	protected VjudegeUserInfo() {
	}
	public VjudegeUserInfo(User user) {
		// TODO Auto-generated constructor stub
		this.user = user;
		this.vjudgeUser = this.user.getVjudgeuser();
	}
	
	public void doGet() {
		// TODO Auto-generated method stub
	}
	
	public void doPost() throws IOException {
		// TODO Auto-generated method stub
		//设置连接时间
//		url = url + vjudgeUser.getVjudgeUserName();	
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6*1000);
		StringBuffer strResult = new StringBuffer();
		//跳过证书检查
		SSLSkip.enableSSL(httpClient);
		
		HttpPost post = new HttpPost(url);
		post.getParams().setParameter("http.socket.timeout", 5000);
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		this.getKeyValue(nvp);
		nvp.add(new BasicNameValuePair("username", vjudgeUser.getVjudgeUserName()));
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvp,
					"utf-8");
			post.setEntity(entity);
			try {
				HttpResponse httpResponse = httpClient.execute(post);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity2 = httpResponse.getEntity();
					strResult.append(EntityUtils.toString(entity2));
					pageAnalysis.Get_Info(strResult);
					this.savaUserInfo();
				}
				else
				{
					System.out.println("获取失败！");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (ConnectTimeoutException e) {
				// TODO: handle exception
				System.out.println("请求Vjudge超时！");
			} catch (SocketTimeoutException e) {
				// TODO: handle exception
				System.out.println("Vjudge响应超时！");
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
				"../AcmRank/src/VjudgeUser.properties"));
		prop.load(in);
		Iterator<String> it = prop.stringPropertyNames().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = prop.getProperty(key);
			nvp.add(new BasicNameValuePair(key, value));
		}
		in.close();
	}
	
	public void savaUserInfo() {
		// TODO Auto-generated method stub
		BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
		int score = 0;
		if (this.vjudgeUser.getVjudgeSolved() == null) {
			score = pageAnalysis.getSoloved();
			SaveLog log = new SaveLog(user, score, (short) 3);
			log.Save();
		} else if (pageAnalysis.getSoloved() > this.vjudgeUser.getVjudgeSolved()) {
			// 保存log信息
			score = pageAnalysis.getSoloved()
					- this.vjudgeUser.getVjudgeSolved();
			SaveLog log = new SaveLog(user, score, (short) 3);
			log.Save();
		}
		this.vjudgeUser.setVjudgeSolved(pageAnalysis.getSoloved());
		this.vjudgeUser.setVjudgeSubmission(pageAnalysis.getSubmitted());
		this.vjudgeUser.setVjudgeUserName(pageAnalysis.getNickName());
		
		vjudgeService.update(this.vjudgeUser);
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
}
