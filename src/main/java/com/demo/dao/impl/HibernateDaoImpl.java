package com.demo.dao.impl;

import com.demo.dao.IHibernateDao;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;


@SuppressWarnings("unchecked")
@Repository
public class HibernateDaoImpl<T> implements IHibernateDao<T> {

    private final SessionFactory session;

    @Autowired
    public HibernateDaoImpl(SessionFactory session) {
        this.session = session;
    }

    @Override
	public Session getCurrentSession() {
		return session.getCurrentSession();
	}

	@Override
	public T get(Class<T> c, String id) {
		return this.getCurrentSession().get(c, id);
	}

	@Override
	public List<T> find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			setQuery(params, q);
		}
		return q.list();
	}

	private void setQuery(Map<String, Object> params, Query q) {
		for (String key : params.keySet()) {
            Object obj = params.get(key);
            // 这里考虑传入的参数是什么类型，不同类型使用的方法不同
            if (obj instanceof Collection<?>) {
                q.setParameterList(key, (Collection<?>) obj);
            } else if (obj instanceof Object[]) {
                q.setParameterList(key, (Object[]) obj);
            } else {
                q.setParameter(key, obj);
            }
        }
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, Integer pageIndex, Integer pageSize) {
		if (pageIndex == null || pageIndex < 0) {
			pageIndex = 0;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			setQuery(params, q);
		}
		return q.setFirstResult(pageIndex).setMaxResults(pageSize).list();
	}

	@Override
	public List<T> findBySQL(Class<T> clazz, String sql) {
		return this.getCurrentSession().createSQLQuery(sql).addEntity(clazz).list();
	}

	@Override
	public List<Map<String, Object>> findBySQL(String sql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public Object findBySQLOne(String sql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).uniqueResult();
	}

	@Override
	public List<T> findBySQL(String sql, Map<String, Object> params, Class<T> clazz) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.addEntity(clazz).list();
	}

	@Override
	public List<T> findBySQLSomeFields(String sql, Map<String, Object> params, Class<T> clazz) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(Transformers.aliasToBean(clazz)).list();
	}

	@Override
	public Integer findBySQLCount(Map<String, Object> params, String sql) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		System.out.println(q.getQueryString());
		List<Number> list = q.list();
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		} else {
			return null;
		}
	}

	@Override
	public Integer findByHQLCount(Map<String, Object> params, String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			setQuery(params, q);
		}
		List<Number> list = q.list();
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		} else {
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> findBySQL(String sql, Map<String, Object> params, Integer pageIndex, Integer pageSize) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		q.setFirstResult(pageIndex).setMaxResults(pageSize);
		return q.list();
	}

	@Override
	public List<Map<String, Object>> findBySQL(String sql) {
		return this.getCurrentSession().createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public Serializable save(T o) throws HibernateException {
		Session session = getCurrentSession();
		Serializable serializable;
		serializable = session.save(o);
		session.flush();
		session.clear();
		return serializable;
	}

	@Override
	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	@Override
	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	@Override
	public Integer executeHql(String hql) {
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	@Override
	public Integer executeSql(String sql) {
		return this.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public Integer executeHql(String hql, Map<String, Object> params) {
		if (params != null && !params.isEmpty()) {
			Query query = this.getCurrentSession().createQuery(hql);
			setQuery(params, query);
			return query.executeUpdate();
		} else {
			return 0;
		}
	}

	@Override
	public Integer executeSql(String sql, Map<String, Object> params) {
		if (params != null && !params.isEmpty()) {
			Query query = this.getCurrentSession().createSQLQuery(sql);
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			return query.executeUpdate();
		} else {
			return 0;
		}
	}

	@Override
	public T getHql(String hql, Map<String, Object> params) {
		if (params != null && !params.isEmpty()) {
			Query query = this.getCurrentSession().createQuery(hql);
			setQuery(params, query);
			return (T) query.uniqueResult();
		} else {
			return null;
		}
	}

	@Override
	public T getSql(String sql, Map<String, Object> params, Class<T> clazz) {
		if (params != null && !params.isEmpty()) {
			Query query = this.getCurrentSession().createSQLQuery(sql).addEntity(clazz);
			Set<String> set = params.keySet();
            for (String key : set) {
                Object value = params.get(key);
                query.setParameter(key, value);
            }
			Object o = query.list().get(0);
			return (T) o;
		} else {
			return null;
		}
	}

	@Override
	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	@Override
	public void saveOrUpdate(List<T> o) {
		Session session = getCurrentSession();
        for (T anO : o) {
            session.saveOrUpdate(anO);
        }
		session.flush();
		session.clear();
	}

	@Override
	public List<Serializable> save(List<T> o) {
		List<Serializable> serializableList = new ArrayList<>();
		Session session = getCurrentSession();
		Serializable serializable;
        for (T anO : o) {
            serializable = session.save(anO);
            serializableList.add(serializable);
        }
		session.flush();
		session.clear();
		return serializableList;
	}

	@Override
	public void delete(List<T> o) throws HibernateException {
		Session session = getCurrentSession();
        for (T anO : o) {
            session.delete(anO);
        }
		session.flush();
		session.clear();
	}
	
}
