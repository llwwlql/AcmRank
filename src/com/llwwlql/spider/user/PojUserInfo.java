package com.llwwlql.spider.user;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.PojUserAnalysis;
import com.llwwlql.bean.Pojuser;

/**
 * 
 * @author 逯其鲁
 * 获取Poj的对应用户信息
 * 
 */
@Entity
public class PojUserInfo implements UserSpider{

	@ManyToOne
	private Pojuser pojUser =null;
	private String url="http://poj.org/userstatus?user_id=";
	protected PojUserInfo() {
	}
	public PojUserInfo(Pojuser pojUser) {
		// TODO Auto-generated constructor stub
		this.pojUser = pojUser;
	}
	public void doGet() {
		// TODO Auto-generated method stub
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6*1000);
		String userName = this.pojUser.getPojUserName();
		try {
			url = url + userName;
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpget);
			StringBuffer strResult = new StringBuffer();
			if(response!=null){
				HttpEntity entity = response.getEntity();
				strResult.append(EntityUtils.toString(entity,"UTF-8"));
				PojUserAnalysis pageAnalysis = new PojUserAnalysis();
				pageAnalysis.Get_Info(strResult);
				this.pojUser.setPojSolved(pageAnalysis.getSolved());
				this.pojUser.setPojSubmission(pageAnalysis.getSubmissions());
			}
			else
			{
				System.out.println("获取失败!");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
