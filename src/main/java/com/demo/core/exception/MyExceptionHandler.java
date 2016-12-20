package com.demo.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 统一异常捕获
 *
 * @author zxd
 * @create 2016-12-20 14:07
 **/

public class MyExceptionHandler implements HandlerExceptionResolver{

    Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("jinlaile");
        // 异常信息打印到控制台，方便调试用
        ex.printStackTrace();
        // 异常信息记录到日志文件
        logger.error(getTraceToString(ex));
        return null;
    }

    /**
     * 异常信息转字符串
     *
     * @param t
     * @return
     */
    public static String getTraceToString(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
