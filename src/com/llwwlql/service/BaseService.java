package com.llwwlql.service;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.llwwlql.bean.User;

@Entity
public class BaseService<T> implements IBaseService<T> {

	public static SessionFactory sessionFactory = new Configuration()
			.configure().buildSessionFactory();

	/**
	 * �������ݲ���
	 * @param user
	 */
	public void save(T user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(user); // ����
			tx.commit();
		} catch (RuntimeException e) {
			// tx.rollback();
			session.getTransaction().rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	/**
	 * ɾ��ָ������
	 * @param user
	 */	
	public void delete(Class userClass, int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			Object user = session.get(userClass, id); // �ȴ����ݿ��ȡ����
			session.delete(user); // ɾ������ʵ�����
			// -------------------------------------
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	/**
	 * �������ݲ���
	 * @param user
	 */
	public void update(T user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(user); // ����
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * ͨ��id��ȡ����
	 * @param userClass
	 * @param id
	 * @return
	 */
	public <T> T getById(Class userClass, int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			T user = (T) session.get(userClass, id); // ��ȡ
			tx.commit();
			return user;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	/**
	 * ͨ��ָ�����Ի�ȡ����
	 * @param userClass
	 * @param userName
	 * @return
	 */
	public List<T> getByParameter(String tableName,String Parameter,String value,String type) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from "+tableName+" where "+Parameter+"=?";
			Query query = session.createQuery(hql);
			//�жϲ�������
			if(type.equals("int") || type.equals("Integer"))
				query.setInteger(0, Integer.parseInt(value));
			else if(type.equals("short") || type.equals("Short"))
				query.setShort(0, Short.parseShort(value));
			else
				query.setString(0, value);
			List<T> user = query.list(); // ��ȡ
			tx.commit();
			return user;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	/**
	 * ģ����ѯ,ֻ����Contest
	 * @param tableName
	 * @param Parameter
	 * @param value
	 * @return
	 */
	public List<T> getByVague(String tableName, String Parameter, String value) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from "+tableName+" where "+Parameter+" like ? and ContestType = ?";
			Query query = session.createQuery(hql);
			//�жϲ�������
			query.setString(0, "%"+value+"%");
			query.setInteger(1, 1);
			List<T> user = query.list(); // ��ȡ
			tx.commit();
			return user;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	/**
	 * ��ѯ����
	 * @return
	 */
	public List<T> findAll(String tableName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<T> list = session.createQuery("FROM "+tableName).list(); // ʹ��HQL��ѯ
			tx.commit();
			return list;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * ��ҳ��ѯ
	 * @param firstResult
	 * 			��ʼ��ȡ�ļ�¼����
	 * @param maxResults
	 * 			����ȡ����������
	 * @return
	 * 			�ܼ�¼�� +��һ������
	 */
	public QueryResult<T> findAll(String tableName,int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			// 1����ѯ�ܼ�¼��
			Long count = (Long) session
					.createQuery("SELECT COUNT(*) FROM "+tableName).uniqueResult(); // ִ�в�ѯ

			// 2����ѯһ������
			Query query = session.createQuery("FROM User");
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			List<T> list = query.list(); // ִ�в�ѯ
			// -------------------------------------
			tx.commit();
			return new QueryResult<T>(list, count);
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}
