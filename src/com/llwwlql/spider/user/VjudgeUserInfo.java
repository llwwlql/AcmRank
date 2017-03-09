package com.llwwlql.spider.user;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.VjudgeUserAnalysis;
import com.llwwlql.bean.User;
import com.llwwlql.bean.Vjudgeuser;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.SSLSkip;
import com.llwwlql.tool.SaveLog;

@Entity
public class VjudgeUserInfo implements Runnable {

	@ManyToOne
	private Vjudgeuser vjudgeUser = null;
	private String url = "https://vjudge.net/user/";
	private VjudgeUserAnalysis pageAnalysis;
	private User user;

	protected VjudgeUserInfo() {
	}

	public VjudgeUserInfo(User user) {
		this.user = user;
		this.vjudgeUser = this.user.getVjudgeuser();
	}

	public void doGet() {
		try {
			HttpClient httpClient = SSLSkip.enableSSL();
			this.url = this.url + vjudgeUser.getVjudgeUserName();
			StringBuffer strResult = new StringBuffer();

			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(2000).setConnectTimeout(2000)
					.setConnectionRequestTimeout(2000).build();
			httpget.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {

				HttpEntity entity = response.getEntity();
				strResult.append(EntityUtils.toString(entity, "UTF-8"));
				EntityUtils.consume(entity);
				pageAnalysis = new VjudgeUserAnalysis();
				pageAnalysis.setUserName(this.vjudgeUser.getVjudgeUserName());
				pageAnalysis.Get_Info(strResult);
			} else {
				System.out.println(this.vjudgeUser.getVjudgeUserName()
						+ "-Vjudge用户名错误");
				this.savaWarningInfo();
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			System.out.println("请求Vjudge超时");
		} catch (SocketTimeoutException e) {
			System.out.println("Vjudge响应超时");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost() throws IOException {
	}

	/**
	 * 获取配置文件的key--value
	 * 
	 * @throws IOException
	 */
	public void getKeyValue(List<NameValuePair> nvp) throws IOException {
		Properties prop = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(
				"VjudgeUser.properties"));
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
		BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
		int score = 0;
		if (this.vjudgeUser.getVjudgeSolved() == null) {
			score = pageAnalysis.getSolved();
			if (score != 0) {
				SaveLog log = new SaveLog(user, score, (short) 7);
				log.Save();
			}
		} else if (pageAnalysis.getSolved() > this.vjudgeUser.getVjudgeSolved()) {
			// 更新Vjudge做题量
			score = pageAnalysis.getSolved()
					- this.vjudgeUser.getVjudgeSolved();
			SaveLog log = new SaveLog(user, score, (short) 7);
			log.Save();
		}
		this.vjudgeUser.setVjudgeSolved(pageAnalysis.getSolved());
		this.vjudgeUser.setVjudgeSubmission(pageAnalysis.getSubmissions());
		this.vjudgeUser.setVjudgeType((short) 1);
		this.vjudgeUser.setVjudgeNickName(this.pageAnalysis.getNickName());
		vjudgeService.update(this.vjudgeUser);
	}

	public void savaWarningInfo() {
		BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
		this.vjudgeUser.setVjudgeType((short) 0);
		vjudgeService.update(this.vjudgeUser);
	}

	public void run() {
		System.out.println("获取Vjudge User Info");
		this.doGet();
		System.out.println("准备保存Vjudge User Info");
		this.savaUserInfo();
		System.out.println("保存完成Vjudge User Info");
	}

	public static void main(String[] args) {
		BaseService<User> userService = new BaseService<User>();
		User user = userService.getById(User.class, 26);
		VjudgeUserInfo userInfo = new VjudgeUserInfo(user);
		userInfo.doGet();
	}
}
