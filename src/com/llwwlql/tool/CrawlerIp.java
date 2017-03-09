package com.llwwlql.tool;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class CrawlerIp {
	private String fileName = "/proxyip.txt";
	HttpHost host = new HttpHost("www.xicidaili.com");
	String pageContent = null;

	public void crawler() throws IOException {
		String path = "http://www.xicidaili.com/nn/";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(path);
		httpGet.setHeader("Accept,text/html",
				"application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.setHeader("Cache-Control", "max-age=0");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader(
				"Cookie",
				"_free_proxy_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJTgyMGZkY2IyNDczM2Q2Zjg2OTBmNjk2NGRkNDdlMzc1BjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMXE2WWNTWWUwZWhoeWowT2tQSXZ2d3N4dnFDRWQ3KzY1RG1TMDZEdXNMeFE9BjsARg%3D%3D--136165fa4c5448584b0afb06504f2569baa0ae03; CNZZDATA1256960793=1158426243-1488006000-%7C1488006000");
		httpGet.setHeader("Host", "www.xicidaili.com");
		httpGet.setHeader("If-None-Match",
				"W/\"451586bf3198c37c087903d290734b16\"");
		httpGet.setHeader("Upgrade-Insecure-Requests", "1");
		httpGet.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		HttpResponse response;
		try {
			response = httpClient.execute(host,httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity resEntity = response.getEntity();
				pageContent = EntityUtils.toString(resEntity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpGet.abort();
	}

	public void analysis() {
		StringBuffer proxyip = new StringBuffer();
		try {
			Parser parser = new Parser(pageContent);
			parser.setEncoding("UTF-8");
			NodeFilter filter = new TagNameFilter("tr");

			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			Node node = nodes.elementAt(1);
			for (int i = 1; i < nodes.size(); i++) {
				node = nodes.elementAt(i);
				proxyip.append(node.getChildren().elementAt(3).getChildren()
						.toHtml()
						+ ":");
				proxyip.append(node.getChildren().elementAt(5).getChildren()
						.toHtml()
						+ "\n");
			}
			FileWriter fw = new FileWriter(fileName);
			fw.write(proxyip.toString());
			fw.close();
		} catch (ParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		CrawlerIp craw = new CrawlerIp();
		try {
			craw.crawler();
			craw.analysis();
			HttpUtils.setProxyIp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
