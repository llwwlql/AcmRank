package com.llwwlql.test;


import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.service.QueryResult;
import com.llwwlql.spider.user.HduUserInfo;


public class testTool {
	
	public static void main(String[] args) throws Exception {
		BaseService<Contest> contestService = new BaseService<Contest>();
		BaseService<Hduuser> hduService = new BaseService<Hduuser>();
		Hduuser hduuser = hduService.getById(Hduuser.class, 1);
		QueryResult<Contest> qResult = contestService.findAll("Contest", 0, 5);
		List<Contest> contests = qResult.getList();
//		JsonObject jsonObject = new JsonObject();
		ContestGson conGson = new ContestGson();
		Gson gson = conGson.getGson();
		
		String json = gson.toJson(contests);
		System.out.println(json);
		
	}
}
