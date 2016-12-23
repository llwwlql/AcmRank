package com.llwwlql.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.llwwlql.bean.Log;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;

public class SaveLog {
	private int score;
	private User user;
	private short origin;
	private String time=null;
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the origin
	 */
	public short getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(short origin) {
		this.origin = origin;
	}
	
	
	public SaveLog(User user, Integer score, short origin) {
		this.score = score;
		this.user = user;
		this.origin = origin;
	}
	
	public SaveLog(User user, Integer score, short origin,String time) {
		this.score = score;
		this.user = user;
		this.origin = origin;
		this.time = time;
	}
	public void Save(){
		BaseService<Log> logService = new BaseService<Log>();
		if(this.time==null)
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.time = df.format(new Date());
		}
		Log log = new Log(user, score, origin, time);
		logService.save(log);
	}
}
