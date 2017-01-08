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
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.Pojuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.SaveLog;

/**
 * 
 * @author ����³
 * ��ȡPoj�Ķ�Ӧ�û���Ϣ
 * 
 */
@Entity
public class PojUserInfo implements UserSpider,Runnable{

	@ManyToOne
	private User user;
	private Pojuser pojUser =null;
	private String url="http://poj.org/userstatus?user_id=";
	private PojUserAnalysis pageAnalysis;
	private static int count=0;
	protected PojUserInfo() {
	}
	public PojUserInfo(User user) {
		// TODO Auto-generated constructor stub
		this.user = user;
		this.pojUser = this.user.getPojuser();
	}
	public void doGet() {
		// TODO Auto-generated method stub
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
				if(strResult.length()>1702)
				{
					count=0;
					pageAnalysis = new PojUserAnalysis();
					pageAnalysis.Get_Info(strResult);
					this.savaUserInfo();
				}
				else if(strResult.length()>1676)
				{//�������Ƶ������
					System.out.println("Poj���ʹ���Ƶ����10s֮���ٷ���");
					try {
						Thread.sleep(10000);
						if(count<3)
						{
							count++;
							System.out.println(count);
							PojUserInfo pojUserInfo = new PojUserInfo(user);
							pojUserInfo.run();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					System.out.println("Poj �û������� ");
					this.savaWarningInfo();
				}
			}
			else
			{
				System.out.println("��ȡʧ��!");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			// TODO: handle exception
			System.out.println("����POJ��ʱ��");
		} catch (SocketTimeoutException e) {
			// TODO: handle exception
			System.out.println("POJ��Ӧ��ʱ��");
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
			//����log��Ϣ
			score = pageAnalysis.getSolved() - this.pojUser.getPojSolved();
			SaveLog log = new SaveLog(user, score, (short)4);
			log.Save();
		}
		this.pojUser.setPojSolved(pageAnalysis.getSolved());
		this.pojUser.setPojSubmission(pageAnalysis.getSubmissions());
		this.pojUser.setPojType((short)1);
		pojUserService.update(this.pojUser);
	}
	public void savaWarningInfo() {
		// TODO Auto-generated method stub
		BaseService<Pojuser> pojUserService = new BaseService<Pojuser>();
		this.pojUser.setPojType((short)0);
		pojUserService.update(this.pojUser);
	}
	public void run() {
		// TODO Auto-generated method stub
		this.doGet();
	}

}
