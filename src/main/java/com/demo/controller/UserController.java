package com.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * user控制器
 *
 * @author zxd
 * @create 2016-12-15 14:40
 **/
@Controller
@RequestMapping("/user")
public class UserController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/hello")
    public String test(){
        logger.info("info============{}","test");
        logger.error("error============={}","hiehie");
        logger.debug("debug==========={}","hhahhahah");
        //        System.out.println("===hi");
        return "hello";
    }

    @RequestMapping("/html")
    public String html(){
        return "html";
    }

    @RequestMapping("/sayHello")
    @ResponseBody
    public String sayHello(){
        return "hahhahahahh";
    }

    @RequestMapping("/view")
    public ModelAndView view(){
        ModelAndView modelAndView = new ModelAndView("html");
        return  modelAndView;
    }

    @RequestMapping("/jspView")
    public ModelAndView jspView(){
        ModelAndView modelAndView = new ModelAndView("hello");
        return modelAndView;
    }



}
