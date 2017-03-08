package com.llwwlql.bean;

import java.util.HashMap;

public class VjudgeCUJson {
	public int id;
	public String title;
	public long begin;
	public long length;
	public Boolean isReplay;	
	public HashMap<String, String[]> participants;
	public Integer[][] submissions;
}
