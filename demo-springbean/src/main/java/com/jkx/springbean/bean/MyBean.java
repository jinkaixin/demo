package com.jkx.springbean.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author jkx
 * @date 2023/10/16
 */
public class MyBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, BeanPostProcessor,
        InitializingBean, DisposableBean {

    public MyBean() {
        System.out.println("bean对象创建了");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("执行了 setBeanFactory");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("执行了 setBeanName, name=" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("执行了 setApplicationContext");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }


    @PostConstruct
    public void postConstruct() {
        System.out.println("执行了 postConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行了 afterPropertiesSet");
    }

    public void initMethod() {
        System.out.println("执行了 initMethod");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("执行了 preDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行了 destroy");
    }

    public void destroyMethod() {
        System.out.println("执行了 destroyMethod");
    }
}
