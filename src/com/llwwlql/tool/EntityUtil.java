package com.llwwlql.tool;

import java.util.Iterator;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Property;

/**
 * ��������������ʵ����õ���Ӧ�ı��������������ֶ��������� </p>
 * ע��po���������Ӧӳ���ļ���һ�£���Student.java��Student.hbm.xml<br>
 * //�޸�Ϊ��ע�ⷽʽ����hbm�ļ��Ѿ�����Ҫ
 * 
 * 
 * @Date��Nov 10, 2008
 * @Time��3:13:07 PM
 * 
 */
public class EntityUtil {

	private static Configuration hibernateConf;

	private static Configuration getHibernateConf() {
		if (hibernateConf == null) {
			// hibernateConf=new Configuration();//*.hbm.xml��ʽ
			hibernateConf = new AnnotationConfiguration().configure();// ע�ⷽʽ
			hibernateConf.buildSessionFactory();// ע�ⷽʽ�����
		}
		return hibernateConf;
	}

	private static PersistentClass getPersistentClass(Class<?> clazz) {
		synchronized (EntityUtil.class) {
			PersistentClass pc = getHibernateConf().getClassMapping(
					clazz.getName());
			if (pc == null) {
				// hibernateConf =
				// getHibernateConf().addClass(clazz);//*.hbm.xml��ʽ
				pc = getHibernateConf().getClassMapping(clazz.getName());

			}
			return pc;
		}
	}

	/**
	 * ������������ȡʵ���Ӧ�ı���
	 * 
	 * @param clazz
	 *            ʵ����
	 * @return ����
	 */
	public static String getTableName(Class<?> clazz) {
		return getPersistentClass(clazz).getTable().getName();
	}

	/**
	 * ������������ȡʵ���Ӧ��������ֶ����ƣ�ֻ������Ψһ���������
	 * 
	 * @param clazz
	 *            ʵ����
	 * @return �����ֶ�����
	 */
	public static String getPrimaryKey(Class<?> clazz) {

		return getPrimaryKeys(clazz).getColumn(0).getName();

	}

	/**
	 * ������������ȡʵ���Ӧ��������ֶ�����
	 * 
	 * @param clazz
	 *            ʵ����
	 * @return ��������primaryKey ������primaryKey.getColumn(i).getName()
	 */
	public static PrimaryKey getPrimaryKeys(Class<?> clazz) {

		return getPersistentClass(clazz).getTable().getPrimaryKey();

	}

	/**
	 * ����������ͨ��ʵ��������ԣ���ȡʵ�������Զ�Ӧ�ı��ֶ�����
	 * 
	 * @param clazz
	 *            ʵ����
	 * @param propertyName
	 *            ��������
	 * @return �ֶ�����
	 */
	public static String getColumnName(Class<?> clazz, String propertyName) {
		PersistentClass persistentClass = getPersistentClass(clazz);
		Property property = persistentClass.getProperty(propertyName);
		Iterator<?> it = property.getColumnIterator();
		if (it.hasNext()) {
			Column column = (Column) it.next();
			return column.getName();
		}
		return null;
	}

}
