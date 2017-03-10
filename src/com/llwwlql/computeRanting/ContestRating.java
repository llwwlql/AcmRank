package com.llwwlql.computeRanting;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.Log;
import com.llwwlql.bean.User;
import com.llwwlql.bean.Vjudgeuser;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.Property;
import com.llwwlql.tool.SaveLog;

public class ContestRating implements BaseCompute, Runnable {

	private Vjudgeuser vjudgeUser;
	private Hduuser hduUser;
	private int rating = 0;
	private String time = null;
	private short origin = 0;
	private User user = null;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 构造函数传入User参数
	 * 
	 * @param user
	 */
	public ContestRating(User user) {
		this.user = user;
		this.hduUser = this.user.getHduuser();
		this.vjudgeUser = this.user.getVjudgeuser();
	}

	public void ComputeContestRank() throws IOException {

		if (this.hduUser.getHduNickName() != null) {
			this.hduContest();
		}
		if (this.vjudgeUser.getVjudgeNickName() != null) {
			this.vjudgeContest();
		}
	}

	public void Compute() {
		BaseService<Log> logService = new BaseService<Log>();
		BaseService<User> userService = new BaseService<User>();

		Map<String, Object> propertyVlaue = new HashMap<String, Object>();
		propertyVlaue.put("user", this.user);
		String[] key = Property.getProperty(propertyVlaue);
		Object[] value = Property.getValue(propertyVlaue);

		List<Log> log = logService.getByParameters("Log", key, value, true);
		for (Log log2 : log) {
			if (log2.getOrigin() == 5 || log2.getOrigin() == 6)
				this.rating += log2.getScore();
		}

		this.user.setContestRating(this.rating);
		userService.update(this.user);
	}

	public int rankScore(int rank) {
		switch (rank) {
		case 1:
			return 6;
		case 2:
			return 4;
		case 3:
			return 3;
		default:
			return 1;
		}
	}

	public void hduContest() {
		BaseService<Contestuser> CUService = new BaseService<Contestuser>();
		BaseService<Log> logService = new BaseService<Log>();

		List<Contestuser> contestUser = CUService.getByParameter("Contestuser",
				"userName", hduUser.getHduNickName());
		for (Contestuser contestuser2 : contestUser) {
			if (contestuser2.getContest().getOrigin() == 1)
			{
				Map<String, Object> propertyVlaue = new HashMap<String, Object>();
				this.origin = 5;
				this.time = contestuser2.getContest().getEndTime();
				propertyVlaue.put("user", this.user);
				propertyVlaue.put("origin", this.origin);
				propertyVlaue.put("time", this.time);
				String[] key = Property.getProperty(propertyVlaue);
				Object[] value = Property.getValue(propertyVlaue);

				List<Log> log = logService.getByParameters("Log", key, value,
						true);
				if (log.size() == 0) {
					SaveLog slog = new SaveLog(user,
							this.rankScore(contestuser2.getRank()),
							this.origin, this.time);
					slog.Save();
				}
			}
		}
	}

	public void vjudgeContest() {
		BaseService<Contestuser> CUService = new BaseService<Contestuser>();
		BaseService<Log> logService = new BaseService<Log>();
		List<Contestuser> contestUser = CUService.getByParameter("Contestuser",
				"userName", vjudgeUser.getVjudgeNickName());
		for (Contestuser contestuser2 : contestUser) {
			if (contestuser2.getContest().getOrigin() == 2) {
				Map<String, Object> propertyVlaue = new HashMap<String, Object>();
				this.origin = 6;
				this.time = contestuser2.getContest().getEndTime();
				propertyVlaue.put("user", this.user);
				propertyVlaue.put("origin", this.origin);
				propertyVlaue.put("time", this.time);
				String[] key = Property.getProperty(propertyVlaue);
				Object[] value = Property.getValue(propertyVlaue);

				List<Log> log = logService.getByParameters("Log", key, value,
						true);
				if (log.size() == 0) {
					SaveLog slog = new SaveLog(user,
							this.rankScore(contestuser2.getRank()), this.origin,time);
					slog.Save();
				}
			}
		}
	}

	public void run() {
		try {
			this.ComputeContestRank();
			this.Compute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
