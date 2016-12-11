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
	 * 保存数据操作
	 * @param user
	 */
	public void save(T user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(user); // 保存
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
	 * 删除指定对象
	 * @param user
	 */	
	public void delete(Class userClass, int id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			Object user = session.get(userClass, id); // 先从数据库获取对象
			session.delete(user); // 删除的是实体对象
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
	 * 更改数据操作
	 * @param user
	 */
	public void update(T user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(user); // 更新
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 通过id获取对象
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
			T user = (T) session.get(userClass, id); // 获取
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
	 * 通过指定属性获取对象
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
			//判断参数属性
			if(type.equals("int") || type.equals("Integer"))
				query.setInteger(0, Integer.parseInt(value));
			else if(type.equals("short") || type.equals("Short"))
				query.setShort(0, Short.parseShort(value));
			else
				query.setString(0, value);
			List<T> user = query.list(); // 获取
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
	 * 模糊查询,只用于Contest
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
			//判断参数属性
			query.setString(0, "%"+value+"%");
			query.setInteger(1, 1);
			List<T> user = query.list(); // 获取
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
	 * 查询所有
	 * @return
	 */
	public List<T> findAll(String tableName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<T> list = session.createQuery("FROM "+tableName).list(); // 使用HQL查询
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
	 * 分页查询
	 * @param firstResult
	 * 			开始获取的记录索引
	 * @param maxResults
	 * 			最多获取多少条数据
	 * @return
	 * 			总记录数 +　一段数据
	 */
	public QueryResult<T> findAll(String tableName,int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			// 1，查询总记录数
			Long count = (Long) session
					.createQuery("SELECT COUNT(*) FROM "+tableName).uniqueResult(); // 执行查询

			// 2，查询一段数据
			Query query = session.createQuery("FROM User");
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			List<T> list = query.list(); // 执行查询
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
