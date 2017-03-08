package com.llwwlql.tool;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpHost;

public class HttpUtils {
	/**
	 * 设置代理ip
	 * 
	 * @throws IOException
	 */
	public static HttpHost setProxyIp() {
		try {
			List<String> ipList = new ArrayList<String>();
			BufferedReader proxyIpReader = new BufferedReader(
					new InputStreamReader(new FileInputStream("/proxyip.txt")));
			String ip = "";
			int count=0;
			while ((ip = proxyIpReader.readLine()) != null) {
				ipList.add(ip);
				count++;
				if(count>30)
					break;
			}
			Random random = new Random();
			int randomInt = random.nextInt(ipList.size());
			String ipport = ipList.get(randomInt);
			String proxyIp = ipport.substring(0, ipport.lastIndexOf(":"));
			String proxyPort = ipport.substring(ipport.lastIndexOf(":") + 1,
					ipport.length());
			HttpHost proxy = new HttpHost(proxyIp,Integer.parseInt(proxyPort));
			return proxy;
		} catch (Exception e) {
			System.out.println("重新设置代理IP");
			setProxyIp();
		}
		return null;
	}
	/**
	 * 测试代理IP是否有效
	 * @param address
	 * @return
	 */
	private static String getHtml(String address){  
        StringBuffer html = new StringBuffer();  
        String result = null;  
        try{  
            URL url = new URL(address);  
            URLConnection conn = url.openConnection();  
            conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");  
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());  
              
            try{  
                String inputLine;  
                byte[] buf = new byte[4096];  
                int bytesRead = 0;  
                while (bytesRead >= 0) {  
                    inputLine = new String(buf, 0, bytesRead, "ISO-8859-1");  
                    html.append(inputLine);  
                    bytesRead = in.read(buf);  
                    inputLine = null;  
                }  
                buf = null;  
            }finally{  
                in.close();  
                conn = null;  
                url = null;  
            }  
            result = new String(html.toString().trim().getBytes("ISO-8859-1"), "gb2312").toLowerCase();  
              
        }catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }finally{  
            html = null;              
        }  
        return result;  
    }
}