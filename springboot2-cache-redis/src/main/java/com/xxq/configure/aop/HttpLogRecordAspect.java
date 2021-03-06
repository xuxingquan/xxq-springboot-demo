package com.xxq.configure.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
@Slf4j(topic = "HttpLogRecord")
public class HttpLogRecordAspect {
    private static final String POST = "POST";
    private static final String GET = "GET";
    // 定义切点Pointcut
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    //@Pointcut("execution(* com.xxq.controller.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        String params = StringUtils.EMPTY;
        //获取请求参数集合并进行遍历拼接
        if (args.length > 0) {
            if (POST.equals(method)) {
                Object object = args[0];
                Map map = getKeyAndValue(object);
                params = JSON.toJSONString(map);
            } else if (GET.equals(method)) {
                params = queryString;
            }
        }
        // result的值就是被拦截方法的返回值
        long start = System.currentTimeMillis();
        Object result;
        try {
            result = pjp.proceed();
        } catch (Exception e) {
            String formatStr = "proceed exception,uri=%s,method=%s,params=[%s],cost=%s";
            log.error(String.format(formatStr, uri, method, params,getCost(start)),e);
            throw e;
        }

        log.info("proceed end,cost={},response={},uri={},method={},params=[{}]",getCost(start), JSON.toJSONString(result), uri, method, params);
        return result;
    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field field = fs[i];
            field.setAccessible(true); // 设置些属性是可以访问的
            Object val;
            try {
                val = field.get(obj);
                // 得到此属性的值
                map.put(field.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                log.error("getKeyAndValue IllegalArgumentException",e);
            } catch (IllegalAccessException e) {
                log.error("getKeyAndValue IllegalAccessException",e);
            }
        }
        return map;
    }

    private long getCost(long start){
        return System.currentTimeMillis() - start;
    }
}