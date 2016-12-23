package com.llwwlql.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.llwwlql.bean.User;

public interface IBaseService<T> {
	/**
	 * 保存数据操作
	 * @param user
	 */
	public void save(T user);

	/**
	 * 删除指定对象
	 * @param user
	 */	
	public void delete(T user);

	/**
	 * 更改数据操作
	 * @param user
	 */
	public void update(T user);

	/**
	 * 通过id获取对象
	 * @param userClass
	 * @param id
	 * @return
	 */
	public <T>T getById(Class userClass,int id);
	
	/**
	 * 通过指定属性查询结果
	 * @param userClass
	 * @param tableName
	 * @param Parameter
	 * @param value
	 * @return
	 */
	public List<T> getByParameter(String tableName,String Parameter,Object value);
	
	/**
	 * 多个参数查询
	 * @param tableName
	 * @param Parameter
	 * @param value
	 * @param rigor
	 * @return
	 */
	public List<T> getByParameters(String tableName,String[] Parameters,Object[] value,boolean rigor);
	
	/**
	 * 模糊查询
	 * @param tableName
	 * @param Parameter
	 * @param value
	 * @return
	 */
	public List<T> getByVague(String tableName,String Parameter,Object value);
	
	/**
	 * 根据HQL语句查询，尽量少用
	 * @param HQL
	 * @return
	 */
	public List<T> getByHQL(String HQL);
		
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> findAll(String tableName);
	
	/**
	 * 分页查询
	 * @param firstResult
	 * 			开始获取的记录索引
	 * @param maxResults
	 * 			最多获取多少条数据
	 * @return
	 * 			总记录数 +　一段数据
	 */
	public QueryResult<T> findAll(String tableName,int firstResult,int maxResults);
}
