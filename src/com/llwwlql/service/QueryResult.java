package com.llwwlql.service;

import java.util.List;
import javax.persistence.Entity;

@Entity
public class QueryResult<T> {
	private List<T> list;
	private Long count;
	protected QueryResult() {
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	public QueryResult(List<T> list, Long count) {
		this.list = list;
		this.count = count;
	}
}
