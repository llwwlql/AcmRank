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
	 * �������ݲ���
	 * 
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
	 * 
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
	 * ͨ��ָ�����Ի�ȡ���� ����String����value
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
			// �жϲ�������
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
	 * ģ����ѯ
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
	 * ���������ѯ
	 * @param tableName ����
	 * @param Parameter ��������������
	 * @param value ��ֵ������
	 * @param rigor �жϾ�ȷ�黹��ģ����true����ȷ��
	 * @return
	 */
	public List<T> getByParameters(String tableName, String[] Parameters,
			Object[] value, boolean rigor) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			StringBuffer hql = new StringBuffer();
			//�ж�ģ���黹�Ǿ�ȷ��
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
	 * ����HQL����ѯ����������
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
			// �жϲ�������
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
	 * 
	 * @return
	 */
	public List<T> findAll(String tableName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<T> list = session.createQuery("FROM " + tableName).list(); // ʹ��HQL��ѯ
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
	 * ����parmeter�����ѯ����
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
			List<T> list = session.createQuery(hql).list(); // ʹ��HQL��ѯ
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
	 * ����parmater�����ѯkey = value��Ԫ��
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
			Query query = session.createQuery(hql); // ʹ��HQL��ѯ
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
	 * ��ҳ��ѯ
	 * 
	 * @param firstResult
	 *            ��ʼ��ȡ�ļ�¼����
	 * @param maxResults
	 *            ����ȡ����������
	 * @return �ܼ�¼�� +��һ������
	 */
	public QueryResult<T> findAll(String tableName, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			// 1����ѯ�ܼ�¼��
			Long count = (Long) session.createQuery(
					"SELECT COUNT(*) FROM " + tableName).uniqueResult(); // ִ�в�ѯ

			// 2����ѯһ������
			Query query = session.createQuery("FROM " + tableName);
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
	
	public QueryResult<T> rankfindAll(String tableName, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			// 1����ѯ�ܼ�¼��
			Long count = (Long) session.createQuery(
					"SELECT COUNT(*) FROM " + tableName).uniqueResult(); // ִ�в�ѯ
			// 2����ѯһ������
			Query query = session.createQuery("FROM " + tableName + " where userType = ? order by rank asc");
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			query.setParameter(0,(short)1);
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
	public QueryResult<T> contestfindAll(String tableName, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			// 1����ѯ�ܼ�¼��
			Long count = (Long) session.createQuery(
					"SELECT COUNT(*) FROM " + tableName).uniqueResult(); // ִ�в�ѯ

			// 2����ѯһ������
			Query query = session.createQuery("FROM " + tableName + " where contestType = ? order by id desc");
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			query.setParameter(0,(short)1);
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
	
	public Long findAllCount(String tableName)
	{
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// -------------------------------------
			// 1����ѯ�ܼ�¼��
			Long count = (Long) session.createQuery(
					"SELECT COUNT(*) FROM " + tableName).uniqueResult(); // ִ�в�ѯ
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
