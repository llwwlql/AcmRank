package com.llwwlql.spider.contest;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.HduContestUPAnalysis;
import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestproblem;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.Property;
import com.llwwlql.tool.SaveLog;

public class HduContestLogin implements Runnable{

	private HttpClient httpClient = new DefaultHttpClient();
	private String loginUrl = "http://acm.hdu.edu.cn/diy/contest_login.php?action=login&cid=";
	private Contest contest = null;
	private HttpHost host = new HttpHost("acm.hdu.edu.cn");
	private String baseUrl = "http://acm.hdu.edu.cn/diy/";
	private String location;
	private int submissions = 0;
	
	public HduContestLogin(Contest contest) {
		// TODO Auto-generated constructor stub
		this.contest = contest;
	}
	
	public void doGet() throws IOException {
		// TODO Auto-generated method stub
		StringBuffer strResult = new StringBuffer();
		location = "http://acm.hdu.edu.cn/diy/contest_ranklist.php?page=1&cid=" + this.contest.getOrginId();
		try {
			HttpGet httpget = new HttpGet(location);
			httpget.getParams().setParameter("http.socket.timeout", 5000);
			HttpResponse response = httpClient.execute(host, httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				// ��ȡ��ҳԴ����Ϣ
				strResult.append(EntityUtils.toString(entity, "GB2312"));
				HduContestUPAnalysis pageAnalysis = new HduContestUPAnalysis(contest);
				pageAnalysis.Get_Info(strResult);
				//�ж��Ƿ񱣴������
				this.saveContestUser(pageAnalysis.getContestUser());
			} else {
				System.out.println(response.getStatusLine().getStatusCode()+"��¼ʧ�ܣ�");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			// TODO: handle exception
			System.out.println("����HDUContest��ϸ��Ϣ��ʱ��");
		} catch (SocketTimeoutException e) {
			// TODO: handle exception
			System.out.println("HDUContest��ϸ��Ϣ��Ӧ��ʱ��");
		}
	}
	
	public void doPost() throws IOException {
		// TODO Auto-generated method stub
		// int contestId = contest.getId();
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 6 * 1000);
		loginUrl = loginUrl + contest.getOrginId();
		try {
			HttpPost httpost = new HttpPost(loginUrl);
			httpost.getParams().setParameter("http.socket.timeout", 5000);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("password", "lduacm"));
			httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset
					.forName("gb2312")));
			HttpResponse response = httpClient.execute(host, httpost);
			if (response.getStatusLine().getStatusCode() == 302) {
				httpost.abort();
				this.doGet();
			} else {
				System.out.println("��¼HDUContestʧ�ܣ�");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ConnectTimeoutException e) {
			// TODO: handle exception
			System.out.println("����HDUContest��¼��ʱ��");
		} catch (SocketTimeoutException e) {
			// TODO: handle exception
			System.out.println("HDUContest��¼��Ӧ��ʱ��");
		} 
	}
	
	public void saveContestUser(List<Contestuser> contestUser) {
		BaseService<Contestuser> cuService = new BaseService<Contestuser>();
		for (Contestuser contestUser2 : contestUser) {
			Map <String,Object> propertyVlaue = new HashMap<String, Object>();
			propertyVlaue.put("contest", contestUser2.getContest());
			propertyVlaue.put("userName", contestUser2.getUserName());
			String[] key = Property.getProperty(propertyVlaue);
			Object[] value = Property.getValue(propertyVlaue);
			
			List<Contestuser> contestUser3 = cuService.getByParameters("Contestuser", key, value, true);
			
			if(contestUser3.size()==0)
			{
				cuService.save(contestUser2);
				this.saveContestProblem(contestUser2,contestUser2.getContestproblems());
				contestUser2.setSubmissions(submissions);
				cuService.update(contestUser2);
			}
			else
			{
				Contestuser CUser = contestUser3.get(0);
				//���±�Ҫ����Ϣ
				this.updateContestProblem(CUser,contestUser2.getContestproblems());
				CUser.setRank(contestUser2.getRank());
				CUser.setSolved(contestUser2.getSolved());
				CUser.setContestproblems(contestUser2.getContestproblems());
				CUser.setPenalty(contestUser2.getPenalty());
				CUser.setSubmissions(submissions);
				cuService.update(CUser);
			}
		}
	}
	
	public void saveContestProblem(Contestuser contestuser,Set<Contestproblem> contestProblems)
	{
		BaseService<Contestproblem> cPService = new BaseService<Contestproblem>();
		submissions = 0;
		for (Contestproblem contestproblem : contestProblems) {
			cPService.save(contestproblem);
			submissions +=contestproblem.getSubmissions();
		}
	}
	
	public void updateContestProblem(Contestuser contestuser , Set<Contestproblem> contestProblems)
	{//��ɾ����������ָ��
		BaseService<Contestproblem> cPService = new BaseService<Contestproblem>();
		Set<Contestproblem> contestproblems2 = contestuser.getContestproblems();
		for (Contestproblem contestproblem : contestproblems2) {
			cPService.delete(contestproblem);
		}
		submissions = 0;
		contestuser.setContestproblems(contestProblems);
		
		for (Contestproblem contestproblem : contestProblems) {
			contestproblem.setContestuser(contestuser);
			cPService.save(contestproblem);
			submissions +=contestproblem.getSubmissions(); 
		}
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
