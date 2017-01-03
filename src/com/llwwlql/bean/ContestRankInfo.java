package com.llwwlql.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ContestRankInfo {
	private String userName;
	private Integer rank;
	private Integer solved;
	private String penalty;
	private List<Contestproblem> contestproblems;
	
	public ContestRankInfo(Integer rank, String userName ,Integer solved,
			String penalty, List contestproblems) {
		super();
		this.rank = rank;
		this.userName = userName;
		this.solved = solved;
		this.penalty = penalty;
		this.contestproblems = contestproblems;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the rank
	 */
	public Integer getRank() {
		return rank;
	}
	/**
	 * @param rank the rank to set
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	/**
	 * @return the solved
	 */
	public Integer getSolved() {
		return solved;
	}
	/**
	 * @param solved the solved to set
	 */
	public void setSolved(Integer solved) {
		this.solved = solved;
	}
	/**
	 * @return the penalty
	 */
	public String getPenalty() {
		return penalty;
	}
	/**
	 * @param penalty the penalty to set
	 */
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}
	/**
	 * @return the contestproblems
	 */
	public List<Contestproblem> getContestproblems() {
		return contestproblems;
	}
	/**
	 * @param contestproblems the contestproblems to set
	 */
	public void setContestproblems(List<Contestproblem> contestproblems) {
		this.contestproblems = contestproblems;
	}
	
	
}
