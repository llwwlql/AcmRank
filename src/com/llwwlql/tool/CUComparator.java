package com.llwwlql.tool;

import java.util.Comparator;

import com.llwwlql.bean.Contestuser;

public class CUComparator implements Comparator<Object>
{
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		Contestuser user1 = (Contestuser)o1;
		Contestuser user2 = (Contestuser)o2;
		if(user1.getRank()>user2.getRank())
			return 1;
		return -1;
	}
}
