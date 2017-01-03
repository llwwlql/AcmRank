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

import com.llwwlql.analysis.HduUserAnalysis;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.Log;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.SaveLog;

@Entity
public class HduUserInfo implements UserSpider,Runnable{
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
		// TODO Auto-generated constructor stub
		this.user = user;
	}

	public void doGet() {
		// TODO Auto-generated method stub
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 6 * 1000);
		this.hduUser = user.getHduuser();
		String userName = hduUser.getHduUserName();
		StringBuffer strResult = new StringBuffer();
		try {
			url = url + userName;
			HttpGet httpget = new HttpGet(url);
			httpget.getParams().setParameter("http.socket.timeout", 5000);
			HttpResponse response = httpClient.execute(httpget);
			if (response != null) {

				HttpEntity entity = response.getEntity();
				// ��ȡ��ҳԴ����Ϣ
				strResult.append(EntityUtils.toString(entity, "UTF-8"));
				if(strResult.length()>7015)
				{
					pageAnalysis = new HduUserAnalysis(url);
					// ��ȡ������֮��Ľ����Ϣ
					pageAnalysis.Get_Info(strResult);
					this.savaUserInfo();
				}
				else
				{
					System.out.println("HDU �û�������");
				}
				
			} else {
				System.out.println("��ȡʧ��!");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			// TODO: handle exception
			System.out.println("����HDU��ʱ��");
		} catch (SocketTimeoutException e) {
			// TODO: handle exception
			System.out.println("HDU��Ӧ��ʱ��");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ����HduUser���º���Ϣ
	 */
	public void savaUserInfo() {
		// TODO Auto-generated method stub
		BaseService<Hduuser> hduUserService = new BaseService<Hduuser>();
		int score = 0;
		if (this.hduUser.getHduSolve() == null) {
			score = pageAnalysis.getProblemsSolved();
			SaveLog log = new SaveLog(user, score, (short) 3);
			log.Save();
		} else if (pageAnalysis.getProblemsSolved() > this.hduUser
				.getHduSolve()) {
			// ����log��Ϣ
			score = pageAnalysis.getProblemsSolved()
					- this.hduUser.getHduSolve();
			SaveLog log = new SaveLog(user, score, (short) 3);
			log.Save();
		}
		this.hduUser.setHduSolve(pageAnalysis.getProblemsSolved());
		this.hduUser.setHduSubmission(pageAnalysis.getSubmissions());
		this.hduUser.setHduNickName(pageAnalysis.getNickName());
		hduUserService.update(this.hduUser);
	}

	public void run() {
		// TODO Auto-generated method stub
		this.doGet();
	}
}
