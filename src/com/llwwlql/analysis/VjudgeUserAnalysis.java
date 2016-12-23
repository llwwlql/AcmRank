package com.llwwlql.analysis;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.llwwlql.bean.Vjudgeuser;

public class VjudgeUserAnalysis implements BaseAnalysis{
	private String UserName;
	private String NickName;
	private Integer Soloved;
	private Integer Submitted;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return NickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		NickName = nickName;
	}

	/**
	 * @return the soloved
	 */
	public Integer getSoloved() {
		return Soloved;
	}

	/**
	 * @param soloved the soloved to set
	 */
	public void setSoloved(Integer soloved) {
		Soloved = soloved;
	}

	/**
	 * @return the submitted
	 */
	public Integer getSubmitted() {
		return Submitted;
	}

	/**
	 * @param submitted the submitted to set
	 */
	public void setSubmitted(Integer submitted) {
		Submitted = submitted;
	}

	public void Get_Info(StringBuffer pageContents) {
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("\\[[0-9](.*?)\\]",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(pageContents.toString());
		String link=null;
		if (m.find())
			 link = m.group().replaceAll("[\\[\"\\]]", "");
		String[] a=link.split(",");
		UserName = a[1];
		NickName = a[2];
		Soloved = Integer.parseInt(a[4]);
		Submitted =Integer.parseInt(a[5]);
		System.out.println(UserName+","+NickName+","+Soloved+","+Submitted);
	}
}
