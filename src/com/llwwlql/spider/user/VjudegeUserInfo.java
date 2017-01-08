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

import com.llwwlql.analysis.HduUserAnalysis;
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
	private String url = "https://vjudge.net/user/";
	private VjudgeUserAnalysis pageAnalysis;
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
		//跳过证书检查
		SSLSkip.enableSSL(httpClient);
		this.url = this.url + vjudgeUser.getVjudgeUserName();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6*1000);
		StringBuffer strResult = new StringBuffer();
		//跳过证书检查
		SSLSkip.enableSSL(httpClient);		
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.getParams().setParameter("http.socket.timeout", 5000);
			HttpResponse response = httpClient.execute(httpget);
			if (response != null) {

				HttpEntity entity = response.getEntity();
				// 获取网页源码信息
				strResult.append(EntityUtils.toString(entity, "UTF-8"));
				if(strResult.length()>7015)
				{
					// 获取到解析之后的结果信息
					pageAnalysis = new VjudgeUserAnalysis();
					pageAnalysis.Get_Info(strResult);
					this.savaUserInfo();
				}
				else
				{
					System.out.println("HDU 用户名错误");
				}
			} else {
				System.out.println("获取失败!");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			// TODO: handle exception
			System.out.println("请求HDU超时！");
		} catch (SocketTimeoutException e) {
			// TODO: handle exception
			System.out.println("HDU响应超时！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doPost() throws IOException {
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
			score = pageAnalysis.getSolved();
			SaveLog log = new SaveLog(user, score, (short) 7);
			log.Save();
		} else if (pageAnalysis.getSolved() > this.vjudgeUser.getVjudgeSolved()) {
			// 保存log信息
			score = pageAnalysis.getSolved()
					- this.vjudgeUser.getVjudgeSolved();
			SaveLog log = new SaveLog(user, score, (short) 7);
			log.Save();
		}
		this.vjudgeUser.setVjudgeSolved(pageAnalysis.getSolved());
		this.vjudgeUser.setVjudgeSubmission(pageAnalysis.getSubmissions());
		this.vjudgeUser.setVjudgeType((short)1);
		vjudgeService.update(this.vjudgeUser);
	}
	public void savaWarningInfo() {
		// TODO Auto-generated method stub
		BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
		this.vjudgeUser.setVjudgeType((short)0);
		vjudgeService.update(this.vjudgeUser);
	}

	public void run() {
		// TODO Auto-generated method stub
		this.doGet();
	}
}
