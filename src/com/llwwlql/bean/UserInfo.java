package com.llwwlql.bean;

public class UserInfo {
	private Integer id;
	private String vjudgeuser;
	private String pojuser;
	private String hduuser;
	private Integer rank;
	private String nickName;
	private String userBlog;
	private String motto;
	private Integer solved;
	private Integer submissions;
	private Integer rating;
	private String email;
	
	public UserInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public UserInfo(Integer id, String vjudgeuser, String pojuser,
			String hduuser, Integer rank, String nickName,
			String userBlog, String motto, Integer solved,
			Integer submissions, Integer rating, String email) {
		this.id = id;
		this.vjudgeuser = vjudgeuser;
		this.pojuser = pojuser;
		this.hduuser = hduuser;
		this.rank = rank;
		this.nickName = nickName;
		this.userBlog = userBlog;
		this.motto = motto;
		this.solved = solved;
		this.submissions = submissions;
		this.rating = rating;
		this.email = email;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the vjudgeuser
	 */
	public String getVjudgeuser() {
		return vjudgeuser;
	}
	/**
	 * @param vjudgeuser the vjudgeuser to set
	 */
	public void setVjudgeuser(String vjudgeuser) {
		this.vjudgeuser = vjudgeuser;
	}
	/**
	 * @return the pojuser
	 */
	public String getPojuser() {
		return pojuser;
	}
	/**
	 * @param pojuser the pojuser to set
	 */
	public void setPojuser(String pojuser) {
		this.pojuser = pojuser;
	}
	/**
	 * @return the hduuser
	 */
	public String getHduuser() {
		return hduuser;
	}
	/**
	 * @param hduuser the hduuser to set
	 */
	public void setHduuser(String hduuser) {
		this.hduuser = hduuser;
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
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the userBlog
	 */
	public String getUserBlog() {
		return userBlog;
	}
	/**
	 * @param userBlog the userBlog to set
	 */
	public void setUserBlog(String userBlog) {
		this.userBlog = userBlog;
	}
	/**
	 * @return the motto
	 */
	public String getMotto() {
		return motto;
	}
	/**
	 * @param motto the motto to set
	 */
	public void setMotto(String motto) {
		this.motto = motto;
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
	 * @return the submissions
	 */
	public Integer getSubmissions() {
		return submissions;
	}
	/**
	 * @param submissions the submissions to set
	 */
	public void setSubmissions(Integer submissions) {
		this.submissions = submissions;
	}
	/**
	 * @return the rating
	 */
	public Integer getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
