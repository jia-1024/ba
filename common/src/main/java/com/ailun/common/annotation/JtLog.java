
package com.ailun.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author JHL
 * @version 1.0
 * @date 2024/10/10 13:34
 * @since : JDK 11
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JtLog {
    /**
     * 默认打印请求参数
     */
    boolean printRequestParam() default true;

    /**
     * 默认打印响应体
     */
    boolean printResponse() default true;
    
    /**
     * 默认打印执行时间
     */
    boolean printTime() default true;
}