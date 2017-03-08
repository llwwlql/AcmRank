package com.llwwlql.tool;

import java.util.Comparator;

import com.llwwlql.bean.Contestproblem;

public class CPComparator implements Comparator<Object>
{
    //这里的o1和o2就是list里任意的两个对象，然后按需求把这个方法填完整就行了

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		Contestproblem problem1 = (Contestproblem)o1;
		Contestproblem problem2 = (Contestproblem)o2;
		if(problem1.getProNumber() > problem2.getProNumber())
			return 1;
		return -1;
	}
}