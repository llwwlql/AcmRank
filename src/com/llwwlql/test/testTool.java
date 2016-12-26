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
import com.llwwlql.tool.BaseGson;


public class testTool {
	
	public static void main(String[] args) throws Exception {
		BaseService<Contest> userService = new BaseService<Contest>();
		
		List<Contest> contests = userService.findAllSort("Contest", "startTime", "asc");
		for(int i=0;i<contests.size();i++)
		{
			Contest contest = contests.get(i);
			contest.setId(i+1);
			userService.update(contest);
		}
	}
}
