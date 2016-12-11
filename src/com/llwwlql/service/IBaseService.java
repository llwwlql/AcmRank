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
	public void delete(Class userClass,int id);

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
	public List<T> getByParameter(String tableName,String Parameter,String value,String type);
	
	/**
	 * ģ����ѯ,ֻ����Contest
	 * @param tableName
	 * @param Parameter
	 * @param value
	 * @return
	 */
	public List<T> getByVague(String tableName,String Parameter,String value);
	
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
