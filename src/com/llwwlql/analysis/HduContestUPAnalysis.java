package com.llwwlql.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestproblem;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;

public class HduContestUPAnalysis implements BaseAnalysis {

	private List<Contestuser> contestUser;
	private Set<Contestproblem> contestProblem;
	private Contest contest = null;
	private Contestuser CUser = null;
	private User user;

	/**
	 * @return the contestUser
	 */
	public List<Contestuser> getContestUser() {
		return contestUser;
	}

	/**
	 * @param contestUser
	 *            the contestUser to set
	 */
	public void setContestUser(List<Contestuser> contestUser) {
		this.contestUser = contestUser;
	}

	public HduContestUPAnalysis(Contest contest) {
		// TODO Auto-generated constructor stub
		this.contest = contest;
	}

	public void Get_Info(StringBuffer pageContents) {
		int Rank;
		String nickName;
		Integer Solved;
		String userPenalty;

		String proNumber;
		String proPenalty;
		int Submissions;
		short AC;
		short firstAC;

		Pattern p = Pattern.compile("<tr\\s*onmouseover\\s*(.*?)</tr>",
				Pattern.CASE_INSENSITIVE);
		contestUser = new ArrayList<Contestuser>();
		Matcher m = p.matcher(pageContents.toString());
		ArrayList<String> linkList = new ArrayList<String>();
		while (m.find()) {
			String link = m.group();
			linkList.add(link);
		}
		// 循环获取
		for (int i = 0; i < linkList.size(); i++) {
			ArrayList<String> linklist = new ArrayList<String>();
			p = Pattern.compile("<td\\s*(.*?)</td>", Pattern.CASE_INSENSITIVE);
			m = p.matcher(linkList.get(i));
			while (m.find()) {
				String link = m.group();
				linklist.add(link);
			}
			Rank = Integer
					.parseInt(linklist.get(0).replaceAll("<\\s*.*?>", ""));
			nickName = linklist.get(1).replaceAll("<\\s*.*?>", "");
			Solved = Integer.parseInt(linklist.get(2).replaceAll("<\\s*.*?>",
					""));
			userPenalty = linklist.get(3).replaceAll("<\\s*.*?>", "");

			// 找到对应的User
			this.findUser(nickName);
			// contestUser赋对象
			CUser = new Contestuser(this.contest, this.user, nickName, Rank,
					Solved, userPenalty);
			//System.out.println("nickName:" + nickName + "\tRank" + Rank + "\tSolved:" + Solved + "Penalty:" +userPenalty);
			// contestProblem开辟空间
			contestProblem = new HashSet<Contestproblem>();
			for (int j = 4; j < linklist.size(); j++) {
				String temp = linklist.get(j);

				proNumber = String.valueOf(j - 3);
				// 00:24:02<br>(-1)
				proPenalty = temp.replaceAll("<\\s*.*?>", "");
				if (linklist.get(j).indexOf("#43CD80") != -1) {
					firstAC = 1;
				} else {
					firstAC = 0;
				}
				if (proPenalty.equalsIgnoreCase("")) {
					Submissions = 0;
					AC = 0;
				} else if (proPenalty.length() == 8) {

					Submissions = 1;
					AC = 1;
				} else if (proPenalty.length() > 0 && proPenalty.length() < 8) {
					AC = 0;
					Submissions = Integer.parseInt(proPenalty.replaceAll(
							"[()-]", ""));
					proPenalty = "";
				} else {
					AC = 1;
					String temp1 = proPenalty;
					proPenalty = temp1.substring(0, 8);
					String x = temp1.substring(8);
					Submissions = Integer.parseInt(x.replaceAll("[()-]", "")) + 1;
				}
				Contestproblem CProblem = new Contestproblem(CUser, proNumber,
						proPenalty, Submissions, AC, firstAC);
				contestProblem.add(CProblem);
			}
			CUser.setContestproblems(contestProblem);
			contestUser.add(CUser);
		}
	}

	private void findUser(String nickName) {
		this.user = null;
		BaseService<Hduuser> hduService = new BaseService<Hduuser>();
		List<Hduuser> hduuser = hduService.getByParameter("Hduuser",
				"hduNickName", nickName);
		if (hduuser.size() != 0)
			this.user = hduuser.get(0).getUser();
	}
}
