package com.llwwlql.analysis;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Entity;

@Entity
public class HduUserAnalysis implements BaseAnalysis {

	private Integer ProblemsSubmitted;
	private Integer ProblemsSolved;
	private Integer Submissions;
	private Integer Accepted;
	
	/**
	 * @return the problemsSubmitted
	 */
	public Integer getProblemsSubmitted() {
		return ProblemsSubmitted;
	}

	/**
	 * @param problemsSubmitted the problemsSubmitted to set
	 */
	public void setProblemsSubmitted(Integer problemsSubmitted) {
		ProblemsSubmitted = problemsSubmitted;
	}

	/**
	 * @return the problemsSolved
	 */
	public Integer getProblemsSolved() {
		return ProblemsSolved;
	}

	/**
	 * @param problemsSolved the problemsSolved to set
	 */
	public void setProblemsSolved(Integer problemsSolved) {
		ProblemsSolved = problemsSolved;
	}

	/**
	 * @return the submissions
	 */
	public Integer getSubmissions() {
		return Submissions;
	}

	/**
	 * @param submissions the submissions to set
	 */
	public void setSubmissions(Integer submissions) {
		Submissions = submissions;
	}

	/**
	 * @return the accepted
	 */
	public Integer getAccepted() {
		return Accepted;
	}

	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(Integer accepted) {
		Accepted = accepted;
	}

	public void Get_Info(StringBuffer pageContents) {
		// TODO Auto-generated method stub
		// 正则表达式校验
		// <tr><td>Problems Solved</td><td align=center>115</td></tr>
		Pattern p = Pattern.compile("<tr><td>(.*?)</tr>",
				Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(pageContents.toString());
		ArrayList<String> linkList = new ArrayList<String>();
		while (m.find()) {
			String link = m.group();
			linkList.add(link);
		}
		int len = linkList.size();
		ProblemsSubmitted = Integer.parseInt(linkList.get(len - 4).replaceAll(
				"[\\s*a-zA-Z<>=\\/]", ""));
		ProblemsSolved = Integer.parseInt(linkList.get(len - 3).replaceAll("[\\s*a-zA-Z<>=\\/]",""));
		Submissions = Integer.parseInt(linkList.get(len - 2)
				.replaceAll("[\\s*a-zA-Z<>=\\/]", ""));
		Accepted = Integer.parseInt(linkList.get(len - 1).replaceAll("[\\s*a-zA-Z<>=\\/]", ""));
		
		//================================
		System.out.println("Solved:" + ProblemsSolved +"\tSubmissions:"+ProblemsSubmitted);
		//================================
	}
}
