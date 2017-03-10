package com.llwwlql.spider.user;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.PojUserAnalysis;
import com.llwwlql.bean.Log;
import com.llwwlql.bean.Pojuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.HttpUtils;
import com.llwwlql.tool.SaveLog;

/**
 * 
 * @author llwwlql
 * 
 */
@Entity
public class PojUserInfo implements UserSpider, Runnable {

	@ManyToOne
	private User user;
	private Pojuser pojUser = null;
	private String url = "http://poj.org/userstatus?user_id=";
	private PojUserAnalysis pageAnalysis;
	private HttpHost proxy;
	private static int judge = 0;

	protected PojUserInfo() {
	}

	public PojUserInfo(User user) {
		this.user = user;
		this.pojUser = this.user.getPojuser();
	}

	public void doGet() {
		String userName = this.pojUser.getPojUserName();
		try {
			url = url + userName;
			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(3000).setConnectTimeout(3000)
					.setProxy(proxy).setConnectionRequestTimeout(3000).build();
			httpget.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpget);
			StringBuffer strResult = new StringBuffer();
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				strResult.append(EntityUtils.toString(entity, "UTF-8"));
				EntityUtils.consume(entity);
				if (strResult.length() > 4000) {
					//重置请求失败次数为0
					this.judge=0;
					pageAnalysis = new PojUserAnalysis();
					pageAnalysis.Get_Info(strResult);
					this.savaUserInfo();
				} else if(strResult.length() == 1702){
					System.out.println("Poj请求频繁，等待10s后再次请求");
					this.resetProxy();
				} else {
					System.out.println("Poj用户名错误");
					this.savaWarningInfo();
				}
			} else {
				System.out.println("Poj请求失败");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			System.out.println("请求Poj超时");
			this.resetProxy();
		} catch (SocketTimeoutException e) {
			System.out.println("Poj响应超时");
			this.resetProxy();
		} catch (ConnectException e) {
			this.resetProxy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void resetProxy(){
		try {
			proxy = HttpUtils.setProxyIp();
			if (this.judge < 2) {
				this.judge++;
				this.doGet();
			}
		} catch (Exception e) {
			System.out.println("PojUserInfo请求失败");
		}
	}

	public void savaUserInfo() {
		BaseService<Pojuser> pojUserService = new BaseService<Pojuser>();
		int score = 0;
		//查看log上的积分状态，做积分匹配，若积分多，则不更新
		BaseService<Log> logService = new BaseService<Log>();
		String hql = "SELECT COUNT(*) FROM Log Where uid='" + user.getId() + "'and origin='4' " ;
		int count = logService.getByHQL(hql).size();
		
		if (this.pojUser.getPojSolved() == null) {
			score = pageAnalysis.getSolved();
			if (score != 0) {
				SaveLog log = new SaveLog(user, score, (short) 4);
				log.Save();
			}

		} else if (pageAnalysis.getSolved() >= count && pageAnalysis.getSolved() > this.pojUser.getPojSolved()) {
			// 更新Poj做题量
			score = pageAnalysis.getSolved() - this.pojUser.getPojSolved();
			SaveLog log = new SaveLog(user, score, (short) 4);
			log.Save();
		}
		this.pojUser.setPojSolved(pageAnalysis.getSolved());
		this.pojUser.setPojSubmission(pageAnalysis.getSubmissions());
		this.pojUser.setPojType((short) 1);
		pojUserService.update(this.pojUser);
	}

	public void savaWarningInfo() {
		BaseService<Pojuser> pojUserService = new BaseService<Pojuser>();
		this.pojUser.setPojType((short) 0);
		pojUserService.update(this.pojUser);
	}

	public void run() {
		this.judge=0;
		proxy = HttpUtils.setProxyIp();
		this.doGet();
	}
}
