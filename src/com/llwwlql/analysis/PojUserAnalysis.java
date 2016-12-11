package com.llwwlql.analysis;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Entity;

@Entity
public class PojUserAnalysis implements BaseAnalysis {
	private int Submissions;
	private int Solved;


	/**
	 * @return the submissions
	 */
	public int getSubmissions() {
		return Submissions;
	}


	/**
	 * @param submissions the submissions to set
	 */
	public void setSubmissions(int submissions) {
		Submissions = submissions;
	}


	/**
	 * @return the solved
	 */
	public int getSolved() {
		return Solved;
	}


	/**
	 * @param solved the solved to set
	 */
	public void setSolved(int solved) {
		Solved = solved;
	}


	public void Get_Info(StringBuffer pageContents) {
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("<a\\s*href\\s*=\\s*status?(.*?)\\s*</a>",
				Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(pageContents.toString());
		ArrayList<String> linkList = new ArrayList<String>();
		while (m.find()) {
			String link = m.group();
			linkList.add(link);
		}
		int len = linkList.size();
		this.Solved = Integer.parseInt(linkList.get(len - 2).replaceAll("<\\s*.*?>", ""));
		this.Submissions = Integer.parseInt(linkList.get(len - 1)
				.replaceAll("<\\s*.*?>\\s*", ""));
	}

}
