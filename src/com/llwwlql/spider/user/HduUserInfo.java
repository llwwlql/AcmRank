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
import org.apache.log4j.chainsaw.Main;
import org.junit.Test;

import com.llwwlql.analysis.HduUserAnalysis;
import com.llwwlql.bean.Hduuser;

@Entity
public class HduUserInfo implements UserSpider{
	@ManyToOne
	private Hduuser hduUser;
	private String url = "http://acm.hdu.edu.cn/userstatus.php?user=";
	protected HduUserInfo() {
	}

	/**
	 * @return the hduUser
	 */
	public HduUserInfo(Hduuser hduUser) {
		// TODO Auto-generated constructor stub
		this.hduUser = hduUser;
	}
	
	public void doGet() {
		// TODO Auto-generated method stub
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6*1000);
		String userName = hduUser.getHduUserName();
		StringBuffer strResult = new StringBuffer();
		try {
			url = url + userName;
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpget);
			if(response!=null){
				
				HttpEntity entity = response.getEntity();
				//获取网页源码信息
				strResult.append(EntityUtils.toString(entity,"UTF-8"));
				HduUserAnalysis pageAnalysis = new HduUserAnalysis();
				//获取到解析之后的结果信息
				pageAnalysis.Get_Info(strResult);
				this.hduUser.setHduSolve(pageAnalysis.getProblemsSolved());
				this.hduUser.setHduSubmission(pageAnalysis.getSubmissions());
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
