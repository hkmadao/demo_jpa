package org.hkmadao.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 静态方法获取 bean
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext != null ? applicationContext.getBean(clazz) : null;
    }

    public static <T> T getBeanByName(String beanName, Class<T> clazz) {
        return applicationContext != null ? applicationContext.getBean(beanName, clazz) : null;
    }

    public static Object getBeanByName(String beanName) {
        return applicationContext != null ? applicationContext.getBean(beanName) : null;
    }

    public static <T> T getProperty(String property, Class<T> clazz) {
        return applicationContext != null ? applicationContext.getEnvironment().getProperty(property, clazz) : null;
    }

    public static String getProperty(String property) {
        return applicationContext != null ? applicationContext.getEnvironment().getProperty(property) : null;
    }
}