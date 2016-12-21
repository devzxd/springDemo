package com.demo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.bean.User;
import com.demo.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * user控制器
 *
 * @author zxd
 * @create 2016-12-15 14:40
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/save")
    @ResponseBody
    public String saveUser(String info) {
        User user = JSON.parseObject(info, User.class);
        userService.save(user);
        String id = user.getId();
        return id;
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateUser(String info) {
        JSONObject jsonObject = JSON.parseObject(info);
        if (!jsonObject.containsKey("id")) {
            return "id不能为空";
        }
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry ignored : jsonObject.entrySet()) {
            map.put(ignored.getKey().toString(), ignored.getValue());
        }
        Integer count = userService.update(map);
        return count.toString();
    }

    @RequestMapping("/hello")
    public String test() {
        logger.info("info============{}", "test");
        logger.error("error============={}", "hiehie");
        logger.debug("debug==========={}", "hhahhahah");
        //        System.out.println("===hi");
        return "hello";
    }

    @RequestMapping("/html")
    public String html() {
        return "html";
    }

    @RequestMapping("/sayHello")
    @ResponseBody
    public String sayHello() {
        return "hahhahahahh";
    }

    @RequestMapping("/view")
    public ModelAndView view() {
        ModelAndView modelAndView = new ModelAndView("html");
        return modelAndView;
    }

    @RequestMapping("/jspView")
    public ModelAndView jspView() {
        ModelAndView modelAndView = new ModelAndView("hello");
        return modelAndView;
    }


}
