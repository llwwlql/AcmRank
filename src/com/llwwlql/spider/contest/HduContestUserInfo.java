package com.llwwlql.spider.contest;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.HduContestUPAnalysis;
import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestproblem;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.Property;

public class HduContestUserInfo implements Runnable {

	private HttpClient httpClient = HttpClientBuilder.create().build();
	private String loginUrl = "http://acm.hdu.edu.cn/diy/contest_login.php?action=login&cid=";
	private Contest contest = null;
	private HttpHost host = new HttpHost("acm.hdu.edu.cn");
	private String location;
	private int submissions = 0;

	public HduContestUserInfo(Contest contest) {
		this.contest = contest;
	}

	public void doGet() throws IOException {
		StringBuffer strResult = new StringBuffer();
		location = "http://acm.hdu.edu.cn/diy/contest_ranklist.php?page=1&cid="
				+ this.contest.getOrginId();
		try {
			HttpGet httpget = new HttpGet(location);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(2000).setConnectTimeout(2000)
					.setConnectionRequestTimeout(2000).build();
			httpget.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(host, httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				// 获取网页源码信息
				strResult.append(EntityUtils.toString(entity, "GB2312"));
				HduContestUPAnalysis pageAnalysis = new HduContestUPAnalysis(
						contest);
				pageAnalysis.Get_Info(strResult);
				// 判断是否保存该数据
				this.saveContestUser(pageAnalysis.getContestUser());
			} else {
				System.out.println(response.getStatusLine().getStatusCode()
						+ "登录失败！");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			System.out.println("请求HDUContest详细信息超时！");
		} catch (SocketTimeoutException e) {
			System.out.println("HDUContest详细信息响应超时！");
		}
	}

	public void doPost() throws IOException {
		// int contestId = contest.getId();
		loginUrl = loginUrl + contest.getOrginId();
		try {
			HttpPost httpost = new HttpPost(loginUrl);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(2000).setConnectTimeout(2000).build();
			httpost.setConfig(requestConfig);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("password", "lduacm"));
			httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset
					.forName("gb2312")));
			HttpResponse response = httpClient.execute(host, httpost);
			if (response.getStatusLine().getStatusCode() == 302) {
				httpost.abort();
				this.doGet();
			} else {
				System.out.println("登录HDUContest失败！");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			System.out.println("请求HDUContest登录超时！");
		} catch (SocketTimeoutException e) {
			System.out.println("HDUContest登录响应超时！");
		}
	}

	@SuppressWarnings("unchecked")
	public void saveContestUser(List<Contestuser> contestUser) {
		BaseService<Contestuser> cuService = new BaseService<Contestuser>();
		BaseService<Contest> contestService = new BaseService<Contest>();
		BaseService<Hduuser> hduService = new BaseService<Hduuser>();
		BaseService<User> userService = new BaseService<User>();
		for (Contestuser contestUser2 : contestUser) {
			Map<String, Object> propertyVlaue = new HashMap<String, Object>();
			propertyVlaue.put("contest", contestUser2.getContest());
			propertyVlaue.put("userName", contestUser2.getUserName());
			String[] key = Property.getProperty(propertyVlaue);
			Object[] value = Property.getValue(propertyVlaue);
			List<Contestuser> contestUser3 = cuService.getByParameters(
					"Contestuser", key, value, true);
			// 找到关联的User
			List<Hduuser> hduUser = hduService.getByParameter("Hduuser",
					"hduNickName", contestUser2.getUserName());
			User user = null;
			;
			if (hduUser.size() > 0)
				user = hduUser.get(0).getUser();
			if (contestUser3.size() == 0) {
				if (user != null) {// 生成User 与 contestUser 的关联
					contestUser2.setUser(user);
					user.getContestusers().add(contestUser2);
					userService.update(user);
				}
				cuService.save(contestUser2);
				this.saveContestProblem(contestUser2,
						contestUser2.getContestproblems());
				contestUser2.setSubmissions(submissions);
				cuService.update(contestUser2);
			} else {
				Contestuser CUser = contestUser3.get(0);
				// 更新必要的信息
				this.updateContestProblem(CUser,
						contestUser2.getContestproblems());
				CUser.setRank(contestUser2.getRank());
				CUser.setSolved(contestUser2.getSolved());
				CUser.setContestproblems(contestUser2.getContestproblems());
				CUser.setPenalty(contestUser2.getPenalty());
				CUser.setSubmissions(submissions);
				cuService.update(CUser);
			}
		}
		contest.setPeopleNum(contestUser.size());
		contestService.update(contest);
	}

	public void saveContestProblem(Contestuser contestuser,
			Set<Contestproblem> contestProblems) {
		BaseService<Contestproblem> cPService = new BaseService<Contestproblem>();
		submissions = 0;
		for (Contestproblem contestproblem : contestProblems) {
			cPService.save(contestproblem);
			submissions += contestproblem.getSubmissions();
		}
	}

	public void updateContestProblem(Contestuser contestuser,
			Set<Contestproblem> contestProblems) {// 先删除，后重新指定
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
			submissions += contestproblem.getSubmissions();
		}
	}

	public void run() {
		try {
			this.doPost();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}