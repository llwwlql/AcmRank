package com.llwwlql.spider.contest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.llwwlql.analysis.HduContestAnalysis;
import com.llwwlql.analysis.HduUserAnalysis;
import com.llwwlql.bean.Contest;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.Property;

public class HduContestInfo implements Runnable,ContestSpider {
	/**
	 * ����Contest�������� con_name_id:LDU search_type:1 1�������ContestName 2����UserName
	 * go:Go
	 */
	private HttpResponse response;
	private HttpEntity entity;
	private String search = "http://acm.hdu.edu.cn/diy/contest_search.php?action=go&content=LDU&types=1&page=";
	private String password = "lduacm";
	private int page=1;
	private HttpHost host = new HttpHost("acm.hdu.edu.cn");
	private HttpGet httpget = null;
	private StringBuffer strResult = null;
	/**
	 * �û���ȡHDU����Ϊ[LDU]��Contest
	 * 
	 * @throws IOException
	 */
	public void doPost() throws IOException {
		// TODO Auto-generated method stub
	}

	public void doGet() throws IOException {
		// TODO Auto-generated method stub
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6*1000);
		try {
			for(page = 0 ;;page++)
			{
				httpget = new HttpGet(search + page);
				httpget.getParams().setParameter("http.socket.timeout", 5000);
				HttpResponse response = httpClient.execute(host,httpget);
				if (response.getStatusLine().getStatusCode()==200) {
					HttpEntity entity = response.getEntity();
					// ��ȡ��ҳԴ����Ϣ
					strResult = new StringBuffer();
					strResult.append(EntityUtils.toString(entity, "GB2312"));
					HduContestAnalysis hduContestAnalysis = new HduContestAnalysis();
					hduContestAnalysis.Get_Info(strResult);	
					//����Contest�����ݿ�
					if(hduContestAnalysis.getContest().size()==0)
					{
						System.out.println(page);
						break;
					}
					this.saveContest(hduContestAnalysis.getContest());
				} else {
					System.out.println("����Contestʧ�ܣ�");
				}
				httpget.abort();
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			// TODO: handle exception
			System.out.println("����HDUContest��ʱ��");
		} catch (SocketTimeoutException e) {
			// TODO: handle exception
			System.out.println("HDUContest��Ӧ��ʱ��");
		} finally {
			EntityUtils.consume(entity);
		}
	}
	
	/**
	 * ����HduContest�����ݿ�
	 * @param hduContest
	 */
	public void saveContest(List<Contest> Contest){
		// �жϸ�Ԫ���Ƿ���ڣ������ڵĻ�����
		BaseService<Contest> contestService = new BaseService<Contest>();
		for (Contest con : Contest) {
			Map <String,Object> propertyVlaue = new HashMap<String, Object>();
			propertyVlaue.put("orginId", con.getOrginId());
			propertyVlaue.put("origin", (short)1);
			String[] key = Property.getProperty(propertyVlaue);
			Object[] value = Property.getValue(propertyVlaue);
			List<Contest> contest2 = contestService.getByParameters("Contest", key, value, true);
			if(contest2.size()==0)
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

	public void run() {
		// TODO Auto-generated method stub
		try {
			this.doGet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
