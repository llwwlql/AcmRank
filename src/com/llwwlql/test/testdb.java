package com.llwwlql.test;

import javax.persistence.Entity;

import com.llwwlql.computeRanting.AllUserInfo;

@Entity
public class testdb {

	public static void main(String[] args) {
		//�������Բ�ѯ
		AllUserInfo allUserInfo = new AllUserInfo();
		System.out.println("Hello World!");
		new Thread(allUserInfo).start();
	}
}