package com.llwwlql.spider.user;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.HduUserAnalysis;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.SaveLog;

@Entity
public class HduUserInfo implements UserSpider, Runnable {
	@ManyToOne
	private Hduuser hduUser;
	private User user;
	private String url = "http://acm.hdu.edu.cn/userstatus.php?user=";
	private HduUserAnalysis pageAnalysis;

	protected HduUserInfo() {
	}

	/**
	 * @return the hduUser
	 */
	public HduUserInfo(User user) {
		this.user = user;
		this.hduUser = user.getHduuser();
	}

	public void doGet() {
		String userName = hduUser.getHduUserName();
		StringBuffer strResult = new StringBuffer();
		try {
			url = url + userName;
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
				if (strResult.length() > 8000) {
					pageAnalysis = new HduUserAnalysis(url);

					pageAnalysis.Get_Info(strResult);
					this.savaUserInfo();
				} else {
					System.out.println("HDU用户名错误");
					this.savaWarningInfo();
				}
			} else {
				System.out.println("请求失败!");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			System.out.println("请求HDU超时");
		} catch (SocketTimeoutException e) {
			System.out.println("HDU响应超时");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新HduUser信息
	 */
	public void savaUserInfo() {
		BaseService<Hduuser> hduUserService = new BaseService<Hduuser>();
		int score = 0;
		if (this.hduUser.getHduSolve() == null) {
			score = pageAnalysis.getProblemsSolved();
			if (score != 0) {
				SaveLog log = new SaveLog(user, score, (short) 3);
				log.Save();
			}
		} else if (pageAnalysis.getProblemsSolved() > this.hduUser
				.getHduSolve()) {
			score = pageAnalysis.getProblemsSolved()
					- this.hduUser.getHduSolve();
			SaveLog log = new SaveLog(user, score, (short) 3);
			log.Save();
		}
		this.hduUser.setHduSolve(pageAnalysis.getProblemsSolved());
		this.hduUser.setHduSubmission(pageAnalysis.getSubmissions());
		this.hduUser.setHduNickName(pageAnalysis.getNickName());
		this.hduUser.setHduType((short) 1);
		hduUserService.update(this.hduUser);
	}

	public void savaWarningInfo() {
		BaseService<Hduuser> hduUserService = new BaseService<Hduuser>();
		this.hduUser.setHduType((short) 0);
		hduUserService.update(this.hduUser);
	}

	public void run() {
		this.doGet();
	}

}
