package com.ailun.common.aspect;


import com.ailun.common.annotation.JtLog;
import com.ailun.common.util.ThreadLocalUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志切面
 *
 * @author JHL
 * @version 1.0
 * @date 2024/10/10 13:34
 * @since : JDK 11
 */
@Aspect
@Component
@SuppressWarnings("all")
public class LogAspect {

    static final String beginTimeKey = "beginTime";

    static final String isPrintRequestParamKey = "isPrintRequestParam";

    static final String isPrintResponseKey = "isPrintResponse";
    
    static final String isPrintTimeKey = "isPrintTime";

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@within(com.ailun.common.annotation.JtLog) || @annotation(com.ailun.common.annotation.JtLog)")
    public void log() {
    }

    /**
     * 前置通知
     */
    @Before("log()")
    public void before(JoinPoint point) {
        getJtLogAnnotationValue(point);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LOG.info(">>>\t[ HTTP_METHOD : {} ]\t", request.getMethod());
        LOG.info(">>>\t[ IP : {} ]\t", request.getRemoteAddr());
        LOG.info(">>>\t[ CLASS_METHOD : {} ]\t", point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        if ((boolean) ThreadLocalUtil.get(isPrintRequestParamKey)) {
            LOG.info(">>>\t[ ARGS : {} ]\t", Arrays.toString(point.getArgs()));
        }
        ThreadLocalUtil.put(beginTimeKey, System.currentTimeMillis());
    }

    /**
     * 后置通知
     */
    @After("log()")
    public void after(JoinPoint point) {
        try {
            printRunTime();
        } catch (Exception e) {
            LOG.error("执行切面记录耗时出错：{}", e.getMessage());
        } finally {
            ThreadLocalUtil.clear();
        }
    }

    /**
     * 环绕通知
     */
    @Around("log()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 执行方法
        Object result = point.proceed();
        return result;
    }

    /**
     * 正常后置通知
     */
    @AfterReturning(pointcut = "log()", returning = "result")
    public void afterReturning(JoinPoint point, Object result) {
        if ((boolean) ThreadLocalUtil.get(isPrintResponseKey)) {
            String msg = ">>>\t[ 方法：{} 返回结果：{} ]\t";
            String methodInfo = point.getSignature().toString();
            String resultStr = null;
            if (null != result) {
                resultStr = result.toString();
            }
            LOG.info(msg, methodInfo, resultStr);
        }
    }
    
    
    /**
     * 异常后置通知
     *
     * @param point join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "log()", throwing = "e")
    public void afterThrowing(JoinPoint point, Throwable e) {
        // 全局异常处理过
        // LOG.error(">>>\t[ 方法：{} 结果：{} ]\t", point.getSignature().toString(), "调用失败", e);
    }
    
    /**
     * 打印执行时长
     */
    private Long printRunTime() {
        Long time = null;
        if ((boolean) ThreadLocalUtil.get(isPrintTimeKey)) {
            time = System.currentTimeMillis() - (long) ThreadLocalUtil.get(beginTimeKey);
            final String log = ">>>\t[ 执行耗时：{} 毫秒 ]\t";
            if (time < 3000) {
                LOG.info(log, time);
            } else if (time < 10000) {
                LOG.warn(log, time);
            } else {
                LOG.error(log, time);
            }
        }
        return time;
    }


    /**
     * 获取调用参数与返回参数配置
     *
     * @param point
     */
    private void getJtLogAnnotationValue(JoinPoint point) {
        boolean isPrintRequestParam = true;
        boolean isPrintResponse = true;
        boolean isPrintTime = true;
        // 获取目标类上的注解
        JtLog classAnnotation = point.getTarget().getClass().getAnnotation(JtLog.class);
        if (classAnnotation != null) {
            isPrintRequestParam = classAnnotation.printRequestParam();
            isPrintResponse = classAnnotation.printResponse();
            isPrintTime = classAnnotation.printTime();
        }
        // 获取目标方法上的注解
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        JtLog methodAnnotation = methodSignature.getMethod().getAnnotation(JtLog.class);
        if (methodAnnotation != null) {
            isPrintRequestParam = methodAnnotation.printRequestParam();
            isPrintResponse = methodAnnotation.printResponse();
            isPrintTime = methodAnnotation.printTime();
        }
        ThreadLocalUtil.put(isPrintRequestParamKey, isPrintRequestParam);
        ThreadLocalUtil.put(isPrintResponseKey, isPrintResponse);
        ThreadLocalUtil.put(isPrintTimeKey, isPrintTime);
    }
}