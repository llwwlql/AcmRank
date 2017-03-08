package com.llwwlql.analysis;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class VjudgeUserAnalysis implements BaseAnalysis {

	private String nickName;
	private Integer Solved;
	private Integer Submissions;
	private String userName;
	
	

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
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}



	/**
	 * @return the solved
	 */
	public Integer getSolved() {
		return Solved;
	}

	/**
	 * @param solved the solved to set
	 */
	public void setSolved(Integer solved) {
		Solved = solved;
	}

	/**
	 * @return the submissions
	 */
	public Integer getSubmissions() {
		return Submissions;
	}

	/**
	 * @param submissions
	 *            the submissions to set
	 */
	public void setSubmissions(Integer submissions) {
		Submissions = submissions;
	}

	public void Get_Info(StringBuffer pageContents) {
		try {
			Parser parser = new Parser(pageContents.toString());
			//获取nickName
			NodeFilter nickNameFilter = new TagNameFilter("span");
			NodeList nickNamenodes = parser.extractAllNodesThatMatch(nickNameFilter);
			//判断该Vjudge用户是否有nickName
			String regex = "\\s*|\t|\r|\n";
			if(nickNamenodes.size()>5)
				this.nickName = nickNamenodes.elementAt(2).getFirstChild().toHtml().replaceAll(regex, "");
			else
			{//没有nickName把userName当做nickName
				this.nickName = this.userName;
			}
			//获取problem数量
			parser = new Parser(pageContents.toString());
			NodeFilter proFilter = new TagNameFilter("td");
			NodeList proNodes = parser.extractAllNodesThatMatch(proFilter);
			Node proNode = proNodes.elementAt(3).getChildren().elementAt(1)
					.getFirstChild();
			this.Solved = Integer.parseInt(proNode.toHtml());
			//获取Submissions数量
			Node submisNode = proNodes.elementAt(4).getChildren().elementAt(1)
					.getFirstChild();
			this.Submissions = this.Solved + Integer.parseInt(submisNode.toHtml());
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
}
