package com.llwwlql.service;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


@Entity
public class BaseService<T> implements IBaseService<T> {

	public static SessionFactory sessionFactory = new Configuration()
			.configure().buildSessionFactory();

	/**
	 * 保存数据操作
	 * 
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
	 * 
	 * @param user
	 */
	public void delete(T user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
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
	 * 
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
	 * 
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
	 * 通过指定属性获取对象 传入String类型value
	 * 
	 * @param userClass
	 * @param userName
	 * @return
	 */
	public List<T> getByParameter(String tableName, String Parameter,
			Object value) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from " + tableName + " where " + Parameter + " =:value";
			Query query = session.createQuery(hql);
			query.setParameter("value", value);
			// 判断参数属性
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
	 * 模糊查询
	 * 
	 * @param tableName
	 * @param Parameter
	 * @param value
	 * @return
	 */
	public List<T> getByVague(String tableName, String Parameter, Object value) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from " + tableName + " where " + Parameter
					+ " like ?";
			Query query = session.createQuery(hql);
			query.setString(0, "%" + value + "%");
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
	 * 多个参数查询
	 * @param tableName 表名
	 * @param Parameter 传参数名的数组
	 * @param value 传值的数组
	 * @param rigor 判断精确查还是模糊查true代表精确查
	 * @return
	 */
	public List<T> getByParameters(String tableName, String[] Parameters,
			Object[] value, boolean rigor) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			StringBuffer hql = new StringBuffer();
			//判断模糊查还是精确查
			String relation = "like";
			if (rigor)
				relation = "=";
			boolean first = true;
			int len = Parameters.length;
			hql.append("from " + tableName);
			for (int i = 0; i < len; i++) {
				if (value[i] != null) {
					if (first) {
						hql.append(" where " + Parameters[i] +" "
								+ relation + " ? ");

						first = false;
					} else {
						hql.append(" and " + Parameters[i] + " "
								+ relation + " ? ");
					}
				}
			}

			Query query = session.createQuery(hql.toString());
			for(int i=0;i<len;i++){
	             if(rigor){
	              query.setParameter(i, value[i]);
	             }else{
	              query.setParameter(i, "%"+value[i]+"%");
	             }
			}
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
	 * 根据HQL语句查询，尽量少用
	 * 
	 * @param HQL
	 * @return
	 */
	public List<T> getByHQL(String HQL) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(HQL);
			// 判断参数属性
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
	 * 
	 * @return
	 */
	public List<T> findAll(String tableName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<T> list = session.createQuery("FROM " + tableName).list(); // 使用HQL查询
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
	 * 根据parmeter排序查询所有
	 * 
	 * @return
	 */
	public List<T> findAllSort(String tableName,String parmeter,String sortType) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "FROM " + tableName + " order by "+ parmeter + " " +sortType;
			List<T> list = session.createQuery(hql).list(); // 使用HQL查询
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
	 * 根据parmater排序查询key = value的元素
	 * 
	 * @return
	 */
	public List<T> findAllSort(String tableName,String key,Object value,String parmeter,String sortType) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "FROM " + tableName +" where "+key+"=?"+ " order by "+ parmeter + " " +sortType;
			Query query = session.createQuery(hql); // 使用HQL查询
			query.setParameter(0, value);
			List<T> list = query.list();
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
	 * 
	 * @param firstResult
	 *            开始获取的记录索引
	 * @param maxResults
	 *            最多获取多少条数据
	 * @return 总记录数 +　一段数据
	 */
	public QueryResult<T> findAll(String tableName, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			// 1，查询总记录数
			Long count = (Long) session.createQuery(
					"SELECT COUNT(*) FROM " + tableName).uniqueResult(); // 执行查询

			// 2，查询一段数据
			Query query = session.createQuery("FROM " + tableName);
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
	
	public QueryResult<T> rankfindAll(String tableName, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			// 1，查询总记录数
			Long count = (Long) session.createQuery(
					"SELECT COUNT(*) FROM " + tableName).uniqueResult(); // 执行查询
			// 2，查询一段数据
			Query query = session.createQuery("FROM " + tableName + " where userType = ? order by rank asc");
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			query.setParameter(0,(short)1);
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
	public QueryResult<T> contestfindAll(String tableName, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			// 1，查询总记录数
			Long count = (Long) session.createQuery(
					"SELECT COUNT(*) FROM " + tableName).uniqueResult(); // 执行查询

			// 2，查询一段数据
			Query query = session.createQuery("FROM " + tableName + " where contestType = ? order by id desc");
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			query.setParameter(0,(short)1);
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
	
	public Long findAllCount(String tableName)
	{
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			// 1，查询总记录数
			Long count = (Long) session.createQuery(
					"SELECT COUNT(*) FROM " + tableName).uniqueResult(); // 执行查询
			// -------------------------------------
			tx.commit();
			return count;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
}
