package com.llwwlql.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestproblem;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.User;
import com.llwwlql.bean.VjudgeCUJson;
import com.llwwlql.bean.Vjudgeuser;
import com.llwwlql.service.BaseService;

/**
 * 解析Vjudge Contest比赛榜单，抽取信息
 * 
 * @author llwwlql
 * 
 */
public class VjudgeCUAnalysis implements BaseAnalysis {
	private List<Contestuser> contestUsers;
	private Set<Contestproblem> contestProblems;
	private Contest contest = null;
	private Contestuser cUser = null;
	private User user;
	private Contestproblem contestProblem = null;
	private Integer proCount;

	/**
	 * @return the contestUsers
	 */
	public List<Contestuser> getContestUsers() {
		return contestUsers;
	}

	/**
	 * @param contestUsers
	 *            the contestUsers to set
	 */
	public void setContestUsers(List<Contestuser> contestUsers) {
		this.contestUsers = contestUsers;
	}

	/**
	 * @return the contestProblems
	 */
	public Set<Contestproblem> getContestProblems() {
		return contestProblems;
	}

	/**
	 * @param contestProblems
	 *            the contestProblems to set
	 */
	public void setContestProblems(Set<Contestproblem> contestProblems) {
		this.contestProblems = contestProblems;
	}

	/**
	 * @return the contest
	 */
	public Contest getContest() {
		return contest;
	}

	/**
	 * @param contest
	 *            the contest to set
	 */
	public void setContest(Contest contest) {
		this.contest = contest;
	}

	/**
	 * @return the cUser
	 */
	public Contestuser getcUser() {
		return cUser;
	}

	/**
	 * @param cUser
	 *            the cUser to set
	 */
	public void setcUser(Contestuser cUser) {
		this.cUser = cUser;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the contestProblem
	 */
	public Contestproblem getContestProblem() {
		return contestProblem;
	}

	/**
	 * @param contestProblem
	 *            the contestProblem to set
	 */
	public void setContestProblem(Contestproblem contestProblem) {
		this.contestProblem = contestProblem;
	}

	/**
	 * @return the proCount
	 */
	public Integer getProCount() {
		return proCount;
	}

	/**
	 * @param proCount
	 *            the proCount to set
	 */
	public void setProCount(Integer proCount) {
		this.proCount = proCount;
	}

	public VjudgeCUAnalysis(Contest contest) {
		this.contest = contest;
	}

	/**
	 * Gson反序列化json字符串为VjudgeCUJson对象 处理获取到Vjudge Contest的Json数据
	 */


	public void Get_Info(StringBuffer pageContents) {

		// 声明一些必要的变量
		try {
			String nickName = null;
			int solvedCnt = 0;
			Long userPenalty = (long) 0;
			int submissions = 0;
			Gson gson = new Gson();
			VjudgeCUJson cuJson = gson.fromJson(pageContents.toString(),
					VjudgeCUJson.class);
			contestUsers = new ArrayList<Contestuser>();
			
			Iterator<Entry<String, String[]>> iter = cuJson.participants.entrySet()
					.iterator();
			List<Integer> isFirst = new ArrayList<Integer>();
			this.setFirstPro(isFirst, cuJson);

			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Integer key = Integer.parseInt(entry.getKey().toString());
				String[] val = (String[]) entry.getValue();
				Map<Integer, Contestproblem> map = new HashMap<Integer, Contestproblem>();
				contestProblems = new HashSet<Contestproblem>();
				cUser = new Contestuser();
				/*
				 * 用户ID ： temp[0] 题号 ：temp[1] 是否AC(1代表AC) ：temp[2] 提交时间： temp[3]
				 */
				for (Integer[] temp : cuJson.submissions) {
					// 循环提交记录，进行扫描
					if (temp[0].equals(key) && temp[3] * 1000 < cuJson.length) {
						// 如何是该用户提交的记录
						if (map.containsKey(temp[1] + 1)) {
							// 该User之前已经提交过该题目
							contestProblem = map.get(temp[1] + 1);
							if (contestProblem.getAcOr() != (short) 1)
								contestProblem.setAcOr((short) (int) temp[2]);

							contestProblem.setPenalty(temp[3].toString());
							contestProblem.setSubmissions(contestProblem
									.getSubmissions() + 1);
						} else {
							// 该User之前还未提交过该题目
							contestProblem = new Contestproblem();
							contestProblem.setProNumber(temp[1] + 1);
							contestProblem.setAcOr((short) (int) temp[2]);
							contestProblem.setPenalty(temp[3].toString());
							contestProblem.setSubmissions(1);
							contestProblem.setContestuser(cUser);
							map.put(temp[1] + 1, contestProblem);
							contestProblems.add(contestProblem);
						}
						// 设置该提交记录为第一个AC
						if (isFirst.contains(temp[3]))
							contestProblem.setFirstAc((short) 1);
					}
				}
				solvedCnt = 0;
				userPenalty = (long) 0;
				submissions = 0;
				// 设置penalty
				for (int i = 1; i <= this.proCount; i++) {
					contestProblem = map.get(i);
					Long temp = (long) 0;
					if (contestProblem != null) {
						if (contestProblem.getAcOr() == (short) 1) {
							temp = Long.parseLong(contestProblem.getPenalty())
									+ (contestProblem.getSubmissions() - 1) * 20
									* 60;
							solvedCnt++;
						}

						userPenalty = userPenalty + temp;
						submissions = submissions + contestProblem.getSubmissions();
						contestProblem.setPenalty(this.setTime(Long
								.parseLong(contestProblem.getPenalty())));
					}
					else
					{
						contestProblem = new Contestproblem(cUser, i, "0:0:0", 0, (short)0, (short)0);
						contestProblems.add(contestProblem);
					}
				}
				// 判断是否存在nickName
				if (val[1].equals(""))
					nickName = val[0];
				else
					nickName = val[1];

				this.findUser(nickName);
				cUser.setContest(this.contest);
				cUser.setUser(this.user);
				cUser.setUserName(nickName);
				cUser.setSolved(solvedCnt);
				cUser.setSubmissions(submissions);
				cUser.setPenalty(userPenalty.toString());
				cUser.setContestproblems(contestProblems);
				if (submissions > 0)
					contestUsers.add(cUser);
			}
			// 排序，计算排名
			this.sort(contestUsers);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("VjugeCUAnalysis 解析异常");
			System.out.println(pageContents);
		}
	}

	/**
	 * 用户比赛排名 格式化用户penalty
	 * 
	 * @param contestUsers
	 */
	public void sort(List<Contestuser> contestUsers) {// 排序计算排名		
		for (int i = 0; i < contestUsers.size()-1; i++) {
			for (int j = i + 1; j < contestUsers.size(); j++) {
				if (contestUsers.get(i).getSolved() == contestUsers.get(j)
						.getSolved()) {
					if (Long.parseLong(contestUsers.get(i).getPenalty()) > Long
							.parseLong(contestUsers.get(j).getPenalty())) {
						this.swap(contestUsers,i,j);
					}
				} else if (contestUsers.get(i).getSolved() < contestUsers
						.get(j).getSolved()) {
					this.swap(contestUsers,i,j);
				}

			}
		}
		for (int i = 0; i < contestUsers.size(); i++) {
			Contestuser contestUser = contestUsers.get(i);
			contestUser.setRank(i + 1);
			Long penalty = Long.parseLong(contestUser.getPenalty());
			contestUser.setPenalty(this.setTime(penalty));
		}
	}

	/**
	 * 排序交换
	 * 
	 * @param user1
	 * @param user2
	 */
	public void swap(List<Contestuser> contestUsers,int i,int j) {
		Contestuser contestUser1 = contestUsers.get(i);
		Contestuser contestUser2 = contestUsers.get(j);
		contestUsers.set(i, contestUser2);
		contestUsers.set(j, contestUser1);
	}

	/**
	 * 设置用时，转换成HH:mm:ss格式
	 * 
	 * @param time
	 * @return
	 */
	public String setTime(Long time) {

		int hour, minute, second;
		second = (int) (time % 60);
		time /= 60;
		minute = (int) (time % 60);
		time /= 60;
		hour = (int) (long) time;
		return hour + ":" + minute + ":" + second;
	}

	/**
	 * 设置第一个做出的题目
	 * 
	 * @param isFirst
	 * @param cuJson
	 */

	public void setFirstPro(List<Integer> isFirst, VjudgeCUJson cuJson) {
		int[] flag = new int[proCount + 1];

		// 赋初值
		for (int i = 0; i < flag.length; i++)
			flag[i] = 0;

		for (Integer[] temp : cuJson.submissions) {
			if (temp[2].equals(1) && flag[temp[1] + 1] == 0) {
				flag[temp[1] + 1] = 1;
				isFirst.add(temp[3]);
			}
		}
	}

	private void findUser(String nickName) {
		this.user = null;
		BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
		List<Vjudgeuser> vjudgeUser = vjudgeService.getByParameter("Vjudgeuser",
				"vjudgeNickName", nickName);
		if (vjudgeUser.size() != 0)
			this.user = vjudgeUser.get(0).getUser();
	}
}