package com.llwwlql.bean;

/**
 * Contestproblem entity. @author MyEclipse Persistence Tools
 */

public class Contestproblem implements java.io.Serializable {

	// Fields

	private Integer id;
	private Contestuser contestuser;
	private String proNumber;
	private String penalty;
	private Integer submissions;
	private short acOr;
	private short firstAc;

	// Constructors

	/** default constructor */
	public Contestproblem() {
	}

	/** full constructor */
	public Contestproblem(Contestuser contestuser, String proNumber,
			String penalty, Integer submissions, short acOr, short firstAc) {
		this.contestuser = contestuser;
		this.proNumber = proNumber;
		this.penalty = penalty;
		this.submissions = submissions;
		this.acOr = acOr;
		this.firstAc = firstAc;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Contestuser getContestuser() {
		return this.contestuser;
	}

	public void setContestuser(Contestuser contestuser) {
		this.contestuser = contestuser;
	}

	public String getProNumber() {
		return this.proNumber;
	}

	public void setProNumber(String proNumber) {
		this.proNumber = proNumber;
	}

	public String getPenalty() {
		return this.penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public Integer getSubmissions() {
		return this.submissions;
	}

	public void setSubmissions(Integer submissions) {
		this.submissions = submissions;
	}

	public short getAcOr() {
		return this.acOr;
	}

	public void setAcOr(short acOr) {
		this.acOr = acOr;
	}

	public short getFirstAc() {
		return this.firstAc;
	}

	public void setFirstAc(short firstAc) {
		this.firstAc = firstAc;
	}

}