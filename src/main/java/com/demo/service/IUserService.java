package com.demo.service;

import com.demo.bean.User;

import java.util.Map;

/**
 * 用户service
 *
 * @author zxd
 * @create 2016-12-20 14:32
 **/
public interface IUserService {
    /**
     * 保存
     *
     * @param user
     */
    void save(User user);


    /**
     * 修改
     *
     * @param map
     * @return
     */
    Integer update(Map<String, Object> map);
}
