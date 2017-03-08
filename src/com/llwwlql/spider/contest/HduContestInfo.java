package com.llwwlql.spider.contest;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.HduContestAnalysis;
import com.llwwlql.bean.Contest;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.Property;

public class HduContestInfo implements Runnable, ContestSpider {
	/**
	 * 作者：llwwlql
	 */

	private HttpEntity entity;
	private String search = "http://acm.hdu.edu.cn/diy/contest_search.php?action=go&content=LDU&types=1&page=";
	private int page = 1;
	private HttpHost host = new HttpHost("acm.hdu.edu.cn");
	private HttpGet httpget = null;
	private StringBuffer strResult = null;

	public void doPost() throws IOException {
	}
	
	/**
	 * get请求获取Hdu Contest信息
	 */

	public void doGet() throws IOException {
		try {
			for (page = 0;; page++) {
				httpget = new HttpGet(search + page);
				RequestConfig requestConfig = RequestConfig.custom()
						.setSocketTimeout(2000).setConnectTimeout(2000)
						.setConnectionRequestTimeout(2000).build();
				httpget.setConfig(requestConfig);
				HttpResponse response = httpClient.execute(host, httpget);
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					// 获取HDU Contest Web资源
					strResult = new StringBuffer();
					strResult.append(EntityUtils.toString(entity, "GB2312"));
					EntityUtils.consume(entity);
					HduContestAnalysis hduContestAnalysis = new HduContestAnalysis();
					hduContestAnalysis.Get_Info(strResult);

					if (hduContestAnalysis.getContest().size() == 0)
						break;
					this.saveContest(hduContestAnalysis.getContest());
				} else {
					System.out.println("请求Contest失败");
				}
				httpget.abort();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			System.out.println("请求HDUContest超时");
		} catch (SocketTimeoutException e) {
			System.out.println("HDUContest响应超时");
		} finally {
			EntityUtils.consume(entity);
		}
	}

	/**
	 * 保存HduContest信息
	 * 
	 * @param hduContest
	 */
	public void saveContest(List<Contest> Contest) {
		BaseService<Contest> contestService = new BaseService<Contest>();
		for (Contest con : Contest) {
			Map<String, Object> propertyVlaue = new HashMap<String, Object>();
			propertyVlaue.put("orginId", con.getOrginId());
			propertyVlaue.put("origin", (short) 1);
			String[] key = Property.getProperty(propertyVlaue);
			Object[] value = Property.getValue(propertyVlaue);
			List<Contest> contest2 = contestService.getByParameters("Contest",
					key, value, true);
			if (contest2.size() == 0) {
				contestService.save(con);
			}
		}
	}

	public static void main(String[] args) {
		HduContestInfo hduInfo = new HduContestInfo();
		try {
			hduInfo.doGet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			this.doGet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
