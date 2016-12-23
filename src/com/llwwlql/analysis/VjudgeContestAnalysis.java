package com.llwwlql.analysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.llwwlql.bean.Contest;

public class VjudgeContestAnalysis implements BaseAnalysis{

	private List<Contest> contest = new ArrayList<Contest>();

	/**
	 * @return the contest
	 */
	public List<Contest> getContest() {
		return contest;
	}

	/**
	 * @param contest
	 *            the contest to set
	 */
	public void setContest(List<Contest> contest) {
		this.contest = contest;
	}

	public void Get_Info(StringBuffer pageContents) {
		// TODO Auto-generated method stub
		// 正则表达式校验
		Integer Contest_ID;
		String Contest_Name;
		String Start_Time = null;
		String End_Time = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long unixStime;
		long unixEtime;
		Pattern p = Pattern.compile("\\[[0-9](.*?)\\]",
				Pattern.CASE_INSENSITIVE);

		String temp = pageContents.toString().replaceAll("\\[Cloned\\]",
				"Cloned");
		Matcher m = p.matcher(temp);
		ArrayList<String> linkList = new ArrayList<String>();
		while (m.find()) {
			String link = m.group();
			linkList.add(link);
		}
		for (int i = 0; i < linkList.size(); i++) {
			String temp1 = linkList.get(i).replaceAll("[\\[\"\\]]", "");
			String[] a = temp1.split(",");
			Contest_ID = Integer.parseInt(a[0]);
			Contest_Name = a[1];
			unixStime = Long.parseLong(a[2]);
			unixEtime = Long.parseLong(a[3]);
			Start_Time = df.format(unixStime);
			End_Time = df.format(unixEtime);
			
			Contest vjContest = new Contest(Contest_Name, Contest_ID,
					Start_Time, End_Time, (short) 2, (short) 1);
			contest.add(vjContest);
		}
	}

}
