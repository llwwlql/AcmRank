package com.llwwlql.spider.contest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.HduContestAnalysis;
import com.llwwlql.analysis.HduUserAnalysis;
import com.llwwlql.bean.Contest;
import com.llwwlql.service.BaseService;

public class HduContestInfo implements ContestSpider {
	/**
	 * 杭电Contest搜索参数 con_name_id:LDU search_type:1 1代表根据ContestName 2代表UserName
	 * go:Go
	 */
	private HttpResponse response;
	private HttpEntity entity;
	private String search = "http://acm.hdu.edu.cn/diy/contest_search.php?action=go&content=LDU&types=1&page=1";
	private String loginUrl = "http://acm.hdu.edu.cn/diy/contest_ranklist.php?page=1&cid=";
	private String password = "lduacm";
	private HttpHost host = new HttpHost("acm.hdu.edu.cn");
	/**
	 * 用户获取HDU上名为[LDU]的Contest
	 * 
	 * @throws IOException
	 */
	public void doPost() throws IOException {
		// TODO Auto-generated method stub
	}

	public void doGet() throws IOException {
		// TODO Auto-generated method stub
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6*1000);
		StringBuffer strResult = new StringBuffer();
		try {
			HttpGet httpget = new HttpGet(search);
			HttpResponse response = httpClient.execute(httpget);
			if (response.getStatusLine().getStatusCode()==200) {
				HttpEntity entity = response.getEntity();
				// 获取网页源码信息
				strResult.append(EntityUtils.toString(entity, "GB2312"));
				HduContestAnalysis hduContestAnalysis = new HduContestAnalysis();
				hduContestAnalysis.Get_Info(strResult);	
				this.saveContest(hduContestAnalysis.getContest());
			} else {
				System.out.println("查找Contest失败！");
			}
			httpget.abort();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			EntityUtils.consume(entity);
		}
	}
	
	/**
	 * 保存HduContest到数据库
	 * @param hduContest
	 */
	public void saveContest(List<Contest> hduContest){
		BaseService<Contest> contestService = new BaseService<Contest>();
		for (Contest con : hduContest) {
			List<Contest> contest = contestService.getByParameter("Contest", "orginId", con.getOrginId().toString(),"int");
			if(contest.size()==0)
			{
				contestService.save(con);
			}
		}
		
	}

	public static void main(String[] args) {
		HduContestInfo hduInfo = new HduContestInfo();
		try {
			hduInfo.doGet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
