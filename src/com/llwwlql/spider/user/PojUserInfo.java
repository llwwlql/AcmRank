package com.llwwlql.spider.user;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.PojUserAnalysis;
import com.llwwlql.bean.Pojuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.SaveLog;

/**
 * 
 * @author 逯其鲁
 * 获取Poj的对应用户信息
 * 
 */
@Entity
public class PojUserInfo implements UserSpider,Runnable{

	@ManyToOne
	private User user;
	private Pojuser pojUser =null;
	private String url="http://poj.org/userstatus?user_id=";
	private PojUserAnalysis pageAnalysis;
	protected PojUserInfo() {
	}
	public PojUserInfo(User user) {
		// TODO Auto-generated constructor stub
		this.user = user;
	}
	public void doGet() {
		// TODO Auto-generated method stub
		this.pojUser = this.user.getPojuser();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6*1000);
		String userName = this.pojUser.getPojUserName();
		try {
			url = url + userName;
			HttpGet httpget = new HttpGet(url);
			httpget.getParams().setParameter("http.socket.timeout", 5000);
			HttpResponse response = httpClient.execute(httpget);
			StringBuffer strResult = new StringBuffer();
			if(response!=null){
				HttpEntity entity = response.getEntity();
				strResult.append(EntityUtils.toString(entity,"UTF-8"));
				if(strResult.length()>2000)
				{
					pageAnalysis = new PojUserAnalysis();
					pageAnalysis.Get_Info(strResult);
					this.savaUserInfo();
				}
				else
				{
					System.out.println("Poj 用户名错误");
				}
			}
			else
			{
				System.out.println("获取失败!");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			// TODO: handle exception
			System.out.println("请求POJ超时！");
		} catch (SocketTimeoutException e) {
			// TODO: handle exception
			System.out.println("POJ响应超时！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void savaUserInfo() {
		// TODO Auto-generated method stub
		BaseService<User> userService = new BaseService<User>();
		BaseService<Pojuser> pojUserService = new BaseService<Pojuser>();
		int score =0;
		if(this.pojUser.getPojSolved()==null)
		{
			score = pageAnalysis.getSolved();
			SaveLog log = new SaveLog(user, score, (short)4);
			log.Save();
		}
		else if(pageAnalysis.getSolved() > this.pojUser.getPojSolved())
		{
			//保存log信息
			score = pageAnalysis.getSolved() - this.pojUser.getPojSolved();
			SaveLog log = new SaveLog(user, score, (short)4);
			log.Save();
		}
		this.pojUser.setPojSolved(pageAnalysis.getSolved());
		this.pojUser.setPojSubmission(pageAnalysis.getSubmissions());
		pojUserService.update(this.pojUser);
	}
	public void run() {
		// TODO Auto-generated method stub
		this.doGet();
	}
}
