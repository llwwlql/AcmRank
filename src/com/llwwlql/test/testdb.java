package com.llwwlql.test;

import java.util.List;

import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.service.QueryResult;
import javax.persistence.Entity;

@Entity
public class testdb {

	public static void main(String[] args) {
		//���Ի�ȡ����ָ������ָ��Լ������
		
		BaseService<Contest> contestService = new BaseService<Contest>();
		
		List<Contest> contest = contestService.getByVague("Contest", "ContestName", "��");
		for (Contest contest2 : contest) {
			System.out.println(contest2.getContestName());
		}
	}
}
