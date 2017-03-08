package com.llwwlql.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.llwwlql.bean.Contest;

//Start、End时间处理有问题
public class HduContestAnalysis implements BaseAnalysis {

	private List<Contest> contest = new ArrayList<Contest>();
	private ArrayList<String> linkList = new ArrayList<String>();

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

	@Test
	public void Get_Info(StringBuffer pageContents) {
		// TODO Auto-generated method stub
		// 正则表达式校验
		// 清空记录
		this.contest.clear();
		Integer Contest_ID;
		String Contest_Name;
		String Start_Time = null;
		String End_Time = null;
		Pattern p = Pattern.compile(
				"<tr><td\\s*width\\s*=\\s*4%\\s*(.*?)</tr>",
				Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(pageContents.toString());
		while (m.find()) {
			String link = m.group();
			linkList.add(link);
		}
		int len = linkList.size();
		for (int i = 0; i < len; i++) {
			p = Pattern.compile("<td(.*?)</td>", Pattern.CASE_INSENSITIVE);
			m = p.matcher(linkList.get(i));
			ArrayList<String> link = new ArrayList<String>();
			while (m.find()) {
				link.add(m.group());
			}
			// 2016-07-14 15:00:00
			Contest_ID = Integer.parseInt(link.get(0).replaceAll("<\\s*.*?>",
					""));
			Contest_Name = link.get(1).replaceAll("<\\s*.*?>", "");
			Start_Time = link.get(2).replaceAll("<\\s*.*?>", "");
			End_Time = link.get(3).replaceAll("<\\s*.*?>", "");

			Contest con = new Contest(Contest_Name, Contest_ID, Start_Time,
					End_Time, (short) 1, (short) 1, 0);
			contest.add(con);
		}
	}
}
