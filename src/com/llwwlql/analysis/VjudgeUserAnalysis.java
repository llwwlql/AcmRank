package com.llwwlql.analysis;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VjudgeUserAnalysis implements BaseAnalysis {
	private int solved;
	private int attempted;
	private int submissions;
	
	
	/**
	 * @return the submissions
	 */
	public int getSubmissions() {
		return submissions;
	}


	/**
	 * @param submissions the submissions to set
	 */
	public void setSubmissions(int submissions) {
		this.submissions = submissions;
	}


	/**
	 * @return the solved
	 */
	public int getSolved() {
		return solved;
	}


	/**
	 * @param solved the solved to set
	 */
	public void setSolved(int solved) {
		this.solved = solved;
	}


	/**
	 * @return the attempted
	 */
	public int getAttempted() {
		return attempted;
	}


	/**
	 * @param attempted the attempted to set
	 */
	public void setAttempted(int attempted) {
		this.attempted = attempted;
	}


	public void Get_Info(StringBuffer pageContents) {
		// TODO Auto-generated method stub
		try {
			Pattern p = Pattern.compile("<a\\s*(.*?)\\s*title=\"Overall solved\"(.*?)</a>",
					Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(pageContents.toString());
			ArrayList<String> linkList=new ArrayList<String>();
			if(m.find()) {
				String link = m.group();
				linkList.add(link);
			}
			String temp = linkList.get(0).replaceAll("\n", "");
			temp=temp.replaceAll("<(.*?)>", "");
			solved=Integer.parseInt(temp);
			
			p = Pattern.compile("<a\\s*(.*?)\\s*title=\"Overall attempted\"(.*?)</a>",
					Pattern.CASE_INSENSITIVE);
			m = p.matcher(pageContents.toString());
			if(m.find()) {
				String link = m.group();
				linkList.add(link);
			}
			temp = linkList.get(1).replaceAll("\n", "");
			temp=temp.replaceAll("<(.*?)>", "");
			attempted=Integer.parseInt(temp);
			this.submissions = this.solved + this.attempted;
		} catch (Exception e) {
			System.out.println("POJ User “Ï≥£");
		}
	}
}
