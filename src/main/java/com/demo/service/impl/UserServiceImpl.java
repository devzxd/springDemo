package com.demo.service.impl;

import com.demo.bean.User;
import com.demo.dao.IHibernateDao;
import com.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 用户实现类
 *
 * @author zxd
 * @create 2016-12-20 14:34
 **/
@Service
public class UserServiceImpl implements IUserService{

    private final IHibernateDao<User> dao;

    @Autowired
    public UserServiceImpl(IHibernateDao<User> dao) {
        this.dao = dao;
    }

    @Override
    public void save(User user) {
        dao.save(user);
    }

    @Override
    public Integer update(Map<String, Object> map) {
        String hql = "update User set ";
        //前台传什么字段就更新什么字段
        for(Map.Entry entry:map.entrySet()){
            if(!entry.getKey().equals("id")){
                hql += entry.getKey() + "= :"+entry.getKey()+",";
            }
        }
        hql = hql.substring(0, hql.length() - 1) + " where id = :id";

        return dao.executeHql(hql,map);
    }

}
