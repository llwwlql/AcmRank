package com.llwwlql.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * Contest entity. @author MyEclipse Persistence Tools
 */

public class Contest implements java.io.Serializable {

	// Fields

	private Integer id;
	private String contestName;
	private Integer orginId;
	private String startTime;
	private String endTime;
	private short origin;
	private short contestType;
	private Set contestusers = new HashSet(0);

	// Constructors

	/** default constructor */
	public Contest() {
	}

	/** minimal constructor */
	public Contest(String contestName, Integer orginId, String startTime,
			String endTime, short origin, short contestType) {
		this.contestName = contestName;
		this.orginId = orginId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.origin = origin;
		this.contestType = contestType;
	}

	/** full constructor */
	public Contest(String contestName, Integer orginId, String startTime,
			String endTime, short origin, short contestType, Set contestusers) {
		this.contestName = contestName;
		this.orginId = orginId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.origin = origin;
		this.contestType = contestType;
		this.contestusers = contestusers;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContestName() {
		return this.contestName;
	}

	public void setContestName(String contestName) {
		this.contestName = contestName;
	}

	public Integer getOrginId() {
		return this.orginId;
	}

	public void setOrginId(Integer orginId) {
		this.orginId = orginId;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public short getOrigin() {
		return this.origin;
	}

	public void setOrigin(short origin) {
		this.origin = origin;
	}

	public short getContestType() {
		return this.contestType;
	}

	public void setContestType(short contestType) {
		this.contestType = contestType;
	}

	public Set getContestusers() {
		return this.contestusers;
	}

	public void setContestusers(Set contestusers) {
		this.contestusers = contestusers;
	}

}