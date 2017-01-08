package com.llwwlql.test;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.llwwlql.analysis.HduUserAnalysis;
import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestproblem;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.Log;
import com.llwwlql.bean.Pojuser;
import com.llwwlql.bean.User;
import com.llwwlql.bean.Vjudgeuser;
import com.llwwlql.service.BaseService;
import com.llwwlql.service.QueryResult;
import com.llwwlql.spider.user.HduUserInfo;
import com.llwwlql.tool.*;

public class testTool {
	
	public static void main(String[] args) throws IOException {
		//清空用户题量信息。
		BaseService<User> userService = new BaseService<User>();
		BaseService<Hduuser> hduService = new BaseService<Hduuser>();
		BaseService<Pojuser> pojService = new BaseService<Pojuser>();
		BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
		BaseService<Log> logService = new BaseService<Log>();
		BaseService<Contestuser> cuService = new BaseService<Contestuser>();
		List<User> users = userService.findAll("User");
		for (User user : users) {
			user.setProblemRating(0);
			user.setSolved(0);
			user.setSubmissions(0);
			user.setCpRating(0);
			user.setContestRating(0);
			userService.update(user);
		}
		List<Hduuser> hduUsers = hduService.findAll("Hduuser");
		for (Hduuser hduuser : hduUsers) {
			hduuser.setHduSolve(0);
			hduuser.setHduSubmission(0);
			hduService.update(hduuser);
		}
		List<Pojuser> pojUsers = pojService.findAll("Pojuser");
		for (Pojuser pojuser : pojUsers) {
			pojuser.setPojSolved(0);
			pojuser.setPojSubmission(0);
			pojService.update(pojuser);
		}
		List<Vjudgeuser> vjudgeUsers = vjudgeService.findAll("Vjudgeuser");
		for (Vjudgeuser vjudgeuser : vjudgeUsers) {
			vjudgeuser.setVjudgeSolved(0);
			vjudgeuser.setVjudgeSubmission(0);
			vjudgeService.update(vjudgeuser);
		}
		
		List<Log> logs = logService.findAll("Log");
		for (Log log : logs) {
			logService.delete(log);
		}
//		List<Contestuser> contestusers = cuService.findAll("Contestuser");
//		for (Contestuser contestuser : contestusers) {
//			cuService.delete(contestuser);
//		}
	}
}
