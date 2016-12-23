package com.llwwlql.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.llwwlql.bean.User;

public interface IBaseService<T> {
	/**
	 * �������ݲ���
	 * @param user
	 */
	public void save(T user);

	/**
	 * ɾ��ָ������
	 * @param user
	 */	
	public void delete(T user);

	/**
	 * �������ݲ���
	 * @param user
	 */
	public void update(T user);

	/**
	 * ͨ��id��ȡ����
	 * @param userClass
	 * @param id
	 * @return
	 */
	public <T>T getById(Class userClass,int id);
	
	/**
	 * ͨ��ָ�����Բ�ѯ���
	 * @param userClass
	 * @param tableName
	 * @param Parameter
	 * @param value
	 * @return
	 */
	public List<T> getByParameter(String tableName,String Parameter,Object value);
	
	/**
	 * ���������ѯ
	 * @param tableName
	 * @param Parameter
	 * @param value
	 * @param rigor
	 * @return
	 */
	public List<T> getByParameters(String tableName,String[] Parameters,Object[] value,boolean rigor);
	
	/**
	 * ģ����ѯ
	 * @param tableName
	 * @param Parameter
	 * @param value
	 * @return
	 */
	public List<T> getByVague(String tableName,String Parameter,Object value);
	
	/**
	 * ����HQL����ѯ����������
	 * @param HQL
	 * @return
	 */
	public List<T> getByHQL(String HQL);
		
	
	/**
	 * ��ѯ����
	 * @return
	 */
	public List<T> findAll(String tableName);
	
	/**
	 * ��ҳ��ѯ
	 * @param firstResult
	 * 			��ʼ��ȡ�ļ�¼����
	 * @param maxResults
	 * 			����ȡ����������
	 * @return
	 * 			�ܼ�¼�� +��һ������
	 */
	public QueryResult<T> findAll(String tableName,int firstResult,int maxResults);
}
