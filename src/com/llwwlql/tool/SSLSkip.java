package com.llwwlql.tool;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class SSLSkip {

	public static void enableSSL(HttpClient httpclient) {

	    // µ÷ÓÃssl

	    try {

	        SSLContext sslcontext = SSLContext.getInstance("TLS");

            TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					// TODO Auto-generated method stub
					
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					// TODO Auto-generated method stub
					
				}

				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}  
               
            };
	        
	        sslcontext.init(null, new TrustManager[]{tm}, null);

	        @SuppressWarnings("deprecation")

	        SSLSocketFactory sf = new SSLSocketFactory(sslcontext);

	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        Scheme https = new Scheme("https", sf, 443);

	        httpclient.getConnectionManager().getSchemeRegistry()

	                .register(https);

	    } catch (Exception e) {

	        e.printStackTrace();

	    }

	}
	
}
