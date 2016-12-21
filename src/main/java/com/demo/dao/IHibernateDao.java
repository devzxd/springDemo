package com.demo.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IHibernateDao<T> {

    T get(Class<T> c, String id) throws HibernateException;

    T getHql(String hql, Map<String, Object> params) throws HibernateException;

    T getSql(String sql, Map<String, Object> params, Class<T> clazz) throws HibernateException;

    List<T> find(String hql) throws HibernateException;

    List<T> find(String hql, Map<String, Object> params) throws HibernateException;

    List<T> find(String hql, Map<String, Object> params, Integer pageIndex, Integer pageSize) throws HibernateException;

    List<T> findBySQL(Class<T> clazz, String sql) throws HibernateException;

    List<Map<String, Object>> findBySQL(String sql);

    List<Map<String, Object>> findBySQL(String sql, Map<String, Object> params);

    List<Map<String, Object>> findBySQL(String sql, Map<String, Object> params, Integer pageIndex, Integer pageSize);

    void saveOrUpdate(T o);

    void saveOrUpdate(List<T> o);

    Serializable save(T o);

    List<Serializable> save(List<T> o);

    void delete(T o) throws HibernateException;

    void delete(List<T> o) throws HibernateException;

    void update(T o) throws HibernateException;

    Integer executeHql(String hql, Map<String, Object> params) throws HibernateException;

    Integer executeHql(String hql) throws HibernateException;

    Integer executeSql(String sql) throws HibernateException;

    Integer executeSql(String sql, Map<String, Object> params) throws HibernateException;

    Session getCurrentSession() throws HibernateException;

    Integer findBySQLCount(Map<String, Object> params, String sql);

    Integer findByHQLCount(Map<String, Object> params, String hql);

    List<T> findBySQL(String sql, Map<String, Object> params, Class<T> clazz);

    List<T> findBySQLSomeFields(String sql, Map<String, Object> params, Class<T> clazz);

    Object findBySQLOne(String sql, Map<String, Object> params);


}
