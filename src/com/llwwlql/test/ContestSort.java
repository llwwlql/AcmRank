package com.llwwlql.test;

import java.util.List;

import com.llwwlql.bean.Contest;
import com.llwwlql.service.BaseService;
import com.llwwlql.service.QueryResult;

public class ContestSort {
	
	public static void sort()
	{
		BaseService<Contest> contestService = new BaseService<Contest>();
		QueryResult<Contest> qcontest = contestService.contestfindAll("Contest", 0, 70);
		List<Contest> contests = qcontest.getList();
		for (Contest contest : contests) {
			System.out.println(contest.getId() + "\t" + contest.getContestName() + "\t" + contest.getStartTime());
		}
		System.out.println(qcontest.getList().size());
	}
	
	public static void main(String[] args) {
		ContestSort.sort();
		//DATE_FORMAT(time,'yyyy-MM-dd HH24-MM-ss')
	}

}
