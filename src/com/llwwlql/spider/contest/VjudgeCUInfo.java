package com.llwwlql.spider.contest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.VjudgeCUAnalysis;
import com.llwwlql.analysis.VjudgeCUproCntAnalysis;
import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestproblem;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.User;
import com.llwwlql.bean.Vjudgeuser;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.Property;
import com.llwwlql.tool.SSLSkip;

/**
 * 获取Vjudge Contest比赛榜单
 * 
 * @author llwwlql
 * 
 */

public class VjudgeCUInfo implements Runnable {

	private HttpClient httpClient;
	private String loginUrl = "https://vjudge.net/user/login";
	private String contestUrl = "https://vjudge.net/contest/";
	private String loginConUrl = "https://vjudge.net/contest/login/";
	private int contestId;
	private int proCount = 0;
	private int submissions = 0;
	private Contest contest = null;

	public VjudgeCUInfo(Contest contest) {
		super();
		this.contest = contest;
		this.contestId = this.contest.getOrginId();
	}

	/**
	 * 用户登录
	 * 
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnsupportedEncodingException
	 */

	public void login() throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException,
			UnsupportedEncodingException {
		httpClient = SSLSkip.enableSSL();
		HttpPost httPost = new HttpPost(loginUrl);
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000)
				.setConnectionRequestTimeout(2000).build();
		httPost.setConfig(requestConfig);

		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("username", "llwwlql"));
		nvp.add(new BasicNameValuePair("password", "love4628"));
		httPost.setEntity(new UrlEncodedFormEntity(nvp, Charset
				.forName("UTF-8")));
		try {
			HttpResponse response = httpClient.execute(httPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				httPost.abort();
			} else
				System.out.println("Vjudge User登录失败");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登录比赛
	 */

	public void loginContest() {
		this.loginConUrl = this.loginConUrl + contestId;
		HttpPost httPost = new HttpPost(loginConUrl);
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		httPost.setConfig(requestConfig);
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("password", "lduacm"));
		httPost.setEntity(new UrlEncodedFormEntity(nvp, Charset
				.forName("UTF-8")));
		try {
			HttpResponse response = httpClient.execute(httPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				httPost.abort();
			} else
				System.out.println("Vjudge Contest登录失败");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取比赛的Json数据
	 */
	public void doGet() {
		this.contestUrl = "https://vjudge.net/data/contest_standing/single/"
				+ contestId + ".json";
		HttpGet httpGet = new HttpGet(this.contestUrl);
		StringBuffer pageContent = new StringBuffer();
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(2000).setConnectTimeout(2000)
					.setConnectionRequestTimeout(2000).build();
			httpGet.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				pageContent.append(EntityUtils.toString(entity, "UTF-8"));
				VjudgeCUAnalysis analysis = new VjudgeCUAnalysis(this.contest);
				httpGet.abort();
				analysis.setProCount(proCount);
				analysis.Get_Info(pageContent);
				this.saveContestUser(analysis.getContestUsers());
			}
		} catch (ClientProtocolException e) {
			System.out.println("网络连接异常！");
		} catch (ConnectTimeoutException e) {
			System.out.println("请求Vjudge Contest Rank超时！");
		} catch (SocketTimeoutException e) {
			System.out.println("Vjudge Contest Rank响应超时！");
		} catch (IOException e) {
			System.out.println("网络连接异常！");
			e.printStackTrace();
		}
	}

	public void getProCount() {
		this.contestUrl = this.contestUrl + contestId;
		HttpGet httpGet = new HttpGet(this.contestUrl);
		StringBuffer pageContent = new StringBuffer();
		try {
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(2000).setConnectTimeout(2000).build();
			httpGet.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (response != null) {
				HttpEntity entity = response.getEntity();
				pageContent.append(EntityUtils.toString(entity, "UTF-8"));
				EntityUtils.consume(entity);
				this.proCount = VjudgeCUproCntAnalysis.getProCount(pageContent);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存ContestUser
	 * 
	 * @param contestUser
	 */
	public void saveContestUser(List<Contestuser> contestUser) {
		BaseService<Contestuser> cuService = new BaseService<Contestuser>();
		BaseService<Contest> contestService = new BaseService<Contest>();
		BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
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
			List<Vjudgeuser> vjudgeUser = vjudgeService.getByParameter(
					"Vjudgeuser", "vjudgeNickName", contestUser2.getUserName());
			User user = null;
			if (vjudgeUser.size() > 0)
				user = vjudgeUser.get(0).getUser();
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

	/**
	 * 保存ContestProblem
	 * 
	 * @param contestuser
	 * @param contestProblems
	 */

	public void saveContestProblem(Contestuser contestuser,
			Set<Contestproblem> contestProblems) {
		BaseService<Contestproblem> cPService = new BaseService<Contestproblem>();
		submissions = 0;
		for (Contestproblem contestproblem : contestProblems) {
			cPService.save(contestproblem);
			submissions += contestproblem.getSubmissions();
		}
	}

	/**
	 * 先删除，后重新指定
	 * 
	 * @param contestuser
	 * @param contestProblems
	 */
	public void updateContestProblem(Contestuser contestuser,
			Set<Contestproblem> contestProblems) {
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
			this.login();
			this.loginContest();
			this.getProCount();
			this.doGet();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException,
			UnsupportedEncodingException {
		BaseService<Contest> contestService = new BaseService<Contest>();
		List<Contest> contests = contestService.getByParameter("Contest",
				"orginId", 152362);
		VjudgeCUInfo vjudgeCU = new VjudgeCUInfo(contests.get(0));
		new Thread(vjudgeCU).start();
	}
}
